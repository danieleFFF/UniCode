import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { FieldRegex } from '../../shared/field-regex';

@Component({
  selector: 'app-changePasswordPopUp',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './changePasswordPopUp.html',
  styleUrls: ['./changePasswordPopUp.scss']
})
export class ChangePasswordPopUp {

  @Output() close = new EventEmitter<void>();

  currentPassword: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private userService: UserService) {}

  public closePopUp(): void {
    this.close.emit();
  }

  public changePassword(): void {
    this.errorMessage = '';
    this.successMessage  = '';

    if (!this.currentPassword || !this.newPassword || !this.confirmNewPassword) {
      this.errorMessage = 'Please fill in all fields';
      return;
    }

    if (this.newPassword !== this.confirmNewPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }

    const passwordError = FieldRegex.validatePassword(this.newPassword);
    if (passwordError) {
      this.errorMessage = passwordError;
      return;
    }
    this.userService.changePassword(this.currentPassword, this.newPassword).subscribe({
      next: () => {
        this.successMessage = 'Password changed successfully!';
      },
      error: (err) => {
        if (err.status === 400) {
          this.errorMessage = 'Incorrect current password';
        } else {
          this.errorMessage = ' Error changing password';
        }
      }
    });
  }
}
