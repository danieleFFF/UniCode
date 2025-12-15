import { Component, Output, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-deleteAccountPopUp',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './deleteAccountPopUp.html',
  styleUrls: ['./deleteAccountPopUp.scss']
})

export class DeleteAccountPopUp {
  @Output() close = new EventEmitter<void>();

  passwordConfirmation: string = '';
  errorMessage: string = '';

  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  public closePopUp(): void {
    this.close.emit();
  }

  public confirmDeleteUser() {

    this.errorMessage = '';

    if (!this.passwordConfirmation) {
      this.errorMessage = 'Inserisci la password';
      return;
    }

    this.userService.deleteUser(this.passwordConfirmation).subscribe({
      next: (response) => {
        console.log(response);
        this.authService.logout();
        this.router.navigate(['/login']);
        this.closePopUp()
      },
      error: (error) => {
        if (error.status === 401 || error.status === 403 || error.status === 400) {
          this.errorMessage = 'Password non corretta';
        } else {
          this.errorMessage = 'Errore';
        }
      }
    })

  }

}
