import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthForm } from '../../shared/auth-form';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegisterPayload } from '../../models/register.model';
import { FieldRegex } from '../../shared/field-regex';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register extends AuthForm{
  username = '';
  confirmPassword = '';

  usernameError: string = '';
  emailError: string = '';
  passwordError: string = '';
  confirmPasswordError: string = '';
  generalError: string = '';

  constructor(
    location: Location,
    private authService: AuthService,
    private router: Router
  ) {
    super(location);
  }

  onSubmit(): void {
    this.usernameError = '';
    this.emailError = '';
    this.passwordError = '';
    this.confirmPasswordError = '';
    this.generalError = '';

    const usernameValidation = FieldRegex.validateUsername(this.username);
    if (usernameValidation) {
      this.usernameError = usernameValidation;
      return;
    }

    const emailValidation = FieldRegex.validateEmail(this.email);
    if (emailValidation) {
      this.emailError = emailValidation;
      return;
    }

    const passwordValidation = FieldRegex.validatePassword(this.password);
    if (passwordValidation) {
      this.passwordError = passwordValidation;
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.confirmPasswordError = "Passwords do not match!";
      return;
    }

    const userData : RegisterPayload = {
      username: this.username,
      email: this.email,
      password: this.password
    };

    this.authService.register(userData).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Registration error:', error);
        if (error.status === 409) {
          this.generalError = 'Username or email already exists.';
        } else if (error.status === 400) {
          this.generalError = 'Invalid input data. Please check your details.';
        } else {
          this.generalError = error.error || 'Registration failed. Please try again later.';
        }
      }
    });
  }
}
