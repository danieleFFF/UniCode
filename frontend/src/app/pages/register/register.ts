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

  constructor(
    location: Location,
    private authService: AuthService,
    private router: Router
  ) {
    super(location);
  }

  onSubmit(): void {

    const usernameError = FieldRegex.validateUsername(this.username);
    if (usernameError) {
      alert(usernameError);
      return;
    }

    const emailError = FieldRegex.validateEmail(this.email);
    if (emailError) {
      alert(emailError);
      return;
    }

    const passwordError = FieldRegex.validatePassword(this.password);
    if (passwordError) {
      alert(passwordError);
      return;
    }

    if (this.password !== this.confirmPassword) {
      alert("Passwords do not match!");
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
        alert('Registration successful! You can now log in.');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Registration error:', error);
        alert(`Error during registration: ${error.error}`);
      }
    });
  }
}
