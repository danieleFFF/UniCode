import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthForm } from '../../shared/auth-form';

@Component({
  selector: 'app-password-recover',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './password-recover.html',
  styleUrls: ['./password-recover.scss']
})
export class PasswordRecover extends AuthForm {

  constructor(location: Location) {
    super(location);
  }

  onSubmit(): void {
    if (!this.email) {
      alert('Please enter your email address.');
      return;
    }
    console.log(`Password recovery request for: ${this.email}`);
    alert('If an account with this email exists, a recovery link has been sent.');
  }
}
