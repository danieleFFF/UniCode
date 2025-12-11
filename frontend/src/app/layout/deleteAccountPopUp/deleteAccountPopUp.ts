import { Component , Output , EventEmitter } from '@angular/core';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-deleteAccountPopUp',
  standalone: true,
  imports: [FormsModule , CommonModule],
  templateUrl: './deleteAccountPopUp.html',
  styleUrl: './deleteAccountPopUp.scss'
})

export class DeleteAccountPopUp {
  @Output() close = new EventEmitter<void>();

  verificaPass : string = "";
  errorMess : string = "";

  constructor(private userService : UserService , private router : Router , private authService : AuthService) {}

  public closePopUp(): void {
    this.close.emit();
  }

  public confirmDeleteUser(){

    this.errorMess = "";

    if(!this.verificaPass){
      this.errorMess = "Inserisci la password";
      return;
    }

    this.userService.deleteUser(this.verificaPass).subscribe({
      next : (response) => {
        console.log(response);
        this.authService.logout();
        this.router.navigate(['/login']);
        this.closePopUp()
      },
      error : (error) => {
        if(error.status == 401 || error.status == 403 || error.status == 400){
          this.errorMess = "Password non corretta"
        }else{
          this.errorMess = "Errore";
        }
      }
    })

  }

}
