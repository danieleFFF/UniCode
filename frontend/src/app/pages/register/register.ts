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
      alert("L'username deve contenere tra 4 e 16 caratteri e almeno una lettera.");
      return;
    }

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(this.email)) {
      alert("Inserisci un indirizzo email valido.");
      return;
    }

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordRegex.test(this.password)) {
      alert("La password deve essere di almeno 8 caratteri e contenere una lettera maiuscola, una minuscola e un numero.");
      return;
    }

    if (this.password !== this.confirmPassword) {
      alert("Le password non coincidono!");
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
        alert('Registrazione avvenuta con successo! Ora puoi effettuare il login.');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Registration error:', error);
        alert(`Errore durante la registrazione: ${error.error}`);
      }
    });
  }
}
