import { Component } from '@angular/core';
<<<<<<< Updated upstream
import { RouterLink } from '@angular/router';
=======
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';
import {RouterLink} from '@angular/router';
>>>>>>> Stashed changes

@Component({
  selector: 'app-login',
  standalone: true,
<<<<<<< Updated upstream
  imports: [RouterLink],
=======
  imports: [CommonModule, FormsModule, RouterLink],
>>>>>>> Stashed changes
  templateUrl: './login.html',
})
<<<<<<< Updated upstream
export class Login {}
=======
export class Login {
  email: string= '';
  password: string= '';
  showPassword: boolean = false;
  constructor(private location: Location) {}
  goBack(){
    this.location.back();
  }
  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  //Disabilita la possibilità di copiare la password con Ctrl+C.
  disableCopy(event: ClipboardEvent) {
    event.preventDefault();
  }

}
>>>>>>> Stashed changes
