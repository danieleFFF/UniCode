import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthForm } from '../../shared/auth-form';
import { Router } from '@angular/router';
import { FieldRegex } from '../../shared/field-regex';
import { AuthService } from '../../services/auth.service';
import { Subscription, timer } from 'rxjs';
import {CredentialsModel} from '../../models/credentials.model';

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
  protected passwordConfirmation: string = '';
  errorMessage: string = '';

  countdown: number = 80;
  showResendButton: boolean = false;
  private timerSubscription: Subscription | undefined;

  constructor(
    location: Location,
    private router: Router,
    private authService: AuthService
  ) {
    super(location);
    // Get email passed from login page
    const navigation = this.router.getCurrentNavigation();
    this.email = navigation?.extras.state?.['email'];
  }

  onSubmit(): void {
    this.errorMessage = '';

    if (this.showResendButton) {
      this.errorMessage = 'The secret code has expired. Please request a new one.';
      return;
    }

    const passwordError = FieldRegex.validatePassword(this.password);
    if (passwordError) {
      this.errorMessage = passwordError;
      return;
    }

    if (this.password !== this.passwordConfirmation) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    if (!this.secretCode) {
      this.errorMessage = 'Please enter the secret code.';
      return;
    }
    const data : CredentialsModel = {
      email: this.email,
      password: this.password
    };
    console.log(`Attempting to reset password with code: ${this.secretCode}`);
    this.authService.resetPassword(data).subscribe({
      next: () => {
        alert('Password has been reset successfully! You can now log in with your new password.');
        this.goBack();
      },
      //TODO :Also here better erro handling
      error: (err) => {
        console.error('Password reset failed:', err);
        this.errorMessage = 'Failed to reset password. Please try again later.';
      }
    });
  }
  toggleSecretCode():void {
    this.showCode = !this.showCode;
  }

  ngOnInit(): void {
    if (!this.email) {
      this.errorMessage = "Email not provided. Please go back to the login page.";
      this.goBack();
      return;
    }
    this.startTimer();
  }

  ngOnDestroy(): void {
    this.timerSubscription?.unsubscribe();
  }

  startTimer(): void {
    this.showResendButton = false;
    this.countdown = 80;
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
    this.authService.sendPasswordRecoverEmail(this.email).subscribe({
      next: () => this.startTimer(),
      error: () => this.errorMessage = "Could not resend code. Please try again later."
    });
  }
}
