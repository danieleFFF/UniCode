import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthForm } from '../../shared/auth-form';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],
  templateUrl: './register.html',
  styleUrls: ['./register.scss'],
})
export class Register extends AuthForm{
  username = '';
  confirmPassword = '';

  constructor(location: Location) {
    super(location);
  }
}
