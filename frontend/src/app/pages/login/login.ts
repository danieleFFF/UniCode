import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { FieldRegex } from '../../shared/field-regex';
import { AuthForm } from '../../shared/auth-form';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.scss', '../../shared/auth-form.scss']

})
export class Login extends AuthForm {
  errorMessage: string = '';
  constructor(
    private router:Router,
    private authService:AuthService,
    private http: HttpClient,
    location:Location
  ){
    super(location);
  }
  onSubmit() {
    console.log('Tentativo di login con:', this.email);
    this.errorMessage = '';

    const emailError = FieldRegex.validateEmail(this.email);
    if (emailError) {
      this.errorMessage = emailError;
      return;
    }

    if (!this.password) {
      this.errorMessage = 'Password is required.';
      return;
    }

    const credentials = {
      email: this.email,
      password: this.password
    };

    this.authService.login(credentials).subscribe({
      next: () => {
        this.http.get<any>(`${environment.apiUrl}/users/profile`, { withCredentials: true })
          .subscribe({
            next: (userDto) => {
              if (userDto.isBanned === true || userDto.banned === true) {
                this.errorMessage = 'Your account has been suspended.';
                this.authService.logout(false);
              } else {
                localStorage.setItem('user', JSON.stringify(userDto));
                this.router.navigate(['/home']);
              }
            },
            error: (err) => {
              console.error('Errore recupero profilo dopo il login', err);
              this.errorMessage = 'Login error. Please try again.';
              this.authService.logout();
            }
          });
      },
      error: (errore) => {
        console.error(errore);
        this.errorMessage = 'Incorrect email or password. Please try again.';
      }
    });
  }
  async passwordRecover(): Promise<void> {
    this.errorMessage = '';

    const emailError = FieldRegex.validateEmail(this.email);
    if (emailError) {
      this.errorMessage = "Please enter a valid email before recovering your password.";
      return;
    }

    this.authService.sendPasswordRecoverEmail(this.email).subscribe({
      next: () => {
        this.router.navigate(['/password-recover'], { state: { email: this.email } });
      },
      error: () => {
        this.errorMessage = "Could not send recovery email. Please try again later.";
      }
    });
  }
}

