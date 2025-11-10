import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthForm } from '../../shared/auth-form';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegisterPayload } from '../../models/register.model';


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

    if (this.username.length < 4 || this.username.length > 16 || !/[a-zA-Z]/.test(this.username)) {
      alert("Username must be between 4 and 16 characters and contain at least one letter.");
      return;
    }

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(this.email)) {
      alert("Please enter a valid email address.");
      return;
    }

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordRegex.test(this.password)) {
      alert("Password must be at least 8 characters long and contain an uppercase letter, a lowercase letter, and a number.");
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
