import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {
  email: string = '';
  password: string = '';
  showPassword: boolean = false;

  constructor(private location: Location) {}

  goBack() {
    this.location.back();
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  disableCopy(event: ClipboardEvent) {
    event.preventDefault();
  }
}
