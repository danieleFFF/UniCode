import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthForm } from '../../shared/auth-form';
import { Router } from '@angular/router';
import { FieldRegex } from '../../shared/field-regex';
import { AuthService } from '../../services/auth.service';
import { Subscription, timer } from 'rxjs';
import { CredentialsModel } from '../../models/credentials.model';

@Component({
  selector: 'app-password-recover',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './password-recover.html',
  styleUrls: ['./password-recover.scss', '../../shared/auth-form.scss']
})
export class PasswordRecover extends AuthForm implements OnInit, OnDestroy {

  protected secretCode: string = '';
  protected showCode: boolean = false;
  protected showPasswordConfirmation: boolean = false;
  protected passwordConfirmation: string = '';

  // Error messages per campo
  protected passwordError: string = '';
  protected passwordConfirmationError: string = '';
  protected secretCodeError: string = '';

  // Messaggi generali
  errorMessage: string = '';
  successMessage: string = '';

  // Timer e stato
  countdown: number = 190;
  showResendButton: boolean = false;
  isSubmitting: boolean = false;
  private timerSubscription: Subscription | undefined;

  constructor(
    location: Location,
    private router: Router,
    private authService: AuthService
  ) {
    super(location);
    const navigation = this.router.getCurrentNavigation();
    this.email = navigation?.extras.state?.['email'];
  }

  ngOnInit(): void {
    if (!this.email) {
      this.errorMessage = "Email not provided. Please go back to the login page.";
      setTimeout(() => this.goBack(), 2000);
      return;
    }
    this.startTimer();
  }

  ngOnDestroy(): void {
    this.timerSubscription?.unsubscribe();
  }

  validatePasswordField(): void {
    this.passwordError = '';

    if (!this.password) {
      return;
    }

    const error = FieldRegex.validatePassword(this.password);
    if (error) {
      this.passwordError = error;
    }

    if (this.passwordConfirmation) {
      this.validatePasswordConfirmationField();
    }
  }

  validatePasswordConfirmationField(): void {
    this.passwordConfirmationError = '';

    if (!this.passwordConfirmation) {
      return;
    }

    if (this.password && this.passwordConfirmation !== this.password) {
      this.passwordConfirmationError = 'Passwords do not match.';
    }
  }

  validateSecretCodeField(): void {
    this.secretCodeError = '';

    if (!this.secretCode) {
      return;
    }

    if (this.secretCode.trim().length === 0) {
      this.secretCodeError = 'Secret code cannot be empty.';
      return;
    }

    if (this.secretCode.length < 4) {
      this.secretCodeError = 'Secret code is too short.';
      return;
    }

    if (this.secretCode.length > 20) {
      this.secretCodeError = 'Secret code is too long.';
    }
  }

  validateAllFields(): boolean {
    this.errorMessage = '';
    this.passwordError = '';
    this.passwordConfirmationError = '';
    this.secretCodeError = '';

    let isValid = true;

    if (this.showResendButton) {
      this.errorMessage = 'The secret code has expired. Please request a new one.';
      return false;
    }

    if (!this.password) {
      this.passwordError = 'Password is required.';
      isValid = false;
    } else {
      const passwordValidation = FieldRegex.validatePassword(this.password);
      if (passwordValidation) {
        this.passwordError = passwordValidation;
        isValid = false;
      }
    }

    if (!this.passwordConfirmation) {
      this.passwordConfirmationError = 'Password confirmation is required.';
      isValid = false;
    } else if (this.password && this.passwordConfirmation !== this.password) {
      this.passwordConfirmationError = 'Passwords do not match.';
      isValid = false;
    }

    // Validazione codice segreto
    if (!this.secretCode) {
      this.secretCodeError = 'Secret code is required.';
      isValid = false;
    } else if (this.secretCode.trim().length === 0) {
      this.secretCodeError = 'Secret code cannot be empty.';
      isValid = false;
    } else if (this.secretCode.length < 4) {
      this.secretCodeError = 'Secret code is too short.';
      isValid = false;
    } else if (this.secretCode.length > 20) {
      this.secretCodeError = 'Secret code is too long.';
      isValid = false;
    }

    return isValid;
  }

  onSubmit(): void {
    if (!this.validateAllFields()) {
      return;
    }

    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const data: CredentialsModel = {
      email: this.email,
      password: this.password,
      secretCode: this.secretCode.trim()
    };

    this.authService.resetPassword(data).subscribe({
      next: () => {
        this.successMessage = 'Password reset successfully! Redirecting to login...';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        console.error(err);
        this.isSubmitting = false;

        // Gestione errori specifici dal server
        if (err.status === 400) {
          this.errorMessage = 'Invalid secret code or password. Please check and try again.';
        } else if (err.status === 404) {
          this.errorMessage = 'Email not found. Please check your email address.';
        } else if (err.status === 401) {
          this.errorMessage = 'Invalid or expired secret code. Please request a new one.';
        } else if (err.status === 429) {
          this.errorMessage = 'Too many attempts. Please try again later.';
        } else if (err.status === 0) {
          this.errorMessage = 'Network error. Please check your connection and try again.';
        } else {
          this.errorMessage = 'Failed to reset password. Please try again later.';
        }
      }
    });
  }

  toggleSecretCode(): void {
    this.showCode = !this.showCode;
  }

  togglePasswordConfirmation(): void {
    this.showPasswordConfirmation = !this.showPasswordConfirmation;
  }

  startTimer(): void {
    this.showResendButton = false;
    this.countdown = 190;
    this.timerSubscription?.unsubscribe();

    this.timerSubscription = timer(0, 1000).subscribe(() => {
      if (this.countdown > 0) {
        this.countdown--;
      } else {
        this.showResendButton = true;
        this.timerSubscription?.unsubscribe();
      }
    });
  }

  resendCode(): void {
    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.authService.sendPasswordRecoverEmail(this.email).subscribe({
      next: () => {
        this.successMessage = 'A new code has been sent to your email.';
        this.startTimer();
        this.isSubmitting = false;

        // Pulisci il codice segreto precedente
        this.secretCode = '';
        this.secretCodeError = '';
      },
      error: (err) => {
        console.error(err);
        this.isSubmitting = false;

        if (err.status === 429) {
          this.errorMessage = 'Too many requests. Please wait a moment before trying again.';
        } else if (err.status === 0) {
          this.errorMessage = 'Network error. Please check your connection and try again.';
        } else {
          this.errorMessage = 'Could not resend code. Please try again later.';
        }
      }
    });
  }

  override goBack(): void {
    this.timerSubscription?.unsubscribe();
    super.goBack();
  }
}
