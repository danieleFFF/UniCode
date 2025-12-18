import { Component, Input } from '@angular/core';
import {ChangePasswordPopUp} from '../../layout/changePasswordPopUp/changePasswordPopUp';
import {DeleteAccountPopUp} from '../../layout/deleteAccountPopUp/deleteAccountPopUp';
import {CommonModule} from '@angular/common';
import {AvatarChoice} from '../../layout/avatarChoicePopUp/avatarChoice';
import { User } from '../../models/user.model';
import {AuthService} from '../../services/auth.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-userSettings',
  standalone: true,
  imports: [
    ChangePasswordPopUp,
    DeleteAccountPopUp,
    CommonModule,
    AvatarChoice
  ],
  templateUrl: './userSettings.html',
  styleUrls: ['./userSettings.scss','../../layout/navbar/navbar.scss']
})
export class UserSettings {

  @Input() user: User | null = null;

  public showChangePasswordPopUp = false;
  public showDeleteAccountPopUp = false;
  public showAvatarChoicePopUp = false;

  constructor(public authService: AuthService, private userService: UserService) {}

  //qui la vera logica , quando si clicca su un bottone si attiva il pop up corrispondente impostando a true la variabile corrispondente
  changePasswordPopUp(){
    this.showChangePasswordPopUp = true;
  }

  deleteAccountPopUp(){
    this.showDeleteAccountPopUp = true;
  }

  //per chiudere invece i popup , quando poi noi cliccheremo annulla allora avremo una cosa del genere
  closeChangePasswordPopUp(){
    this.showChangePasswordPopUp = false;
  }

  closeDeleteAccountPopUp(){
    this.showDeleteAccountPopUp = false;
  }

  avatarChoicePopUp(){
    this.showAvatarChoicePopUp = true;
  }

  closeAvatarChoicePopUp(){
    this.showAvatarChoicePopUp = false;
  }

  logout(): void {
    this.authService.logout();
  }
  onAvatarUpdated() {
    console.log("Il popup ha salvato! Ricarico i dati utente...");
    this.userService.getProfile().subscribe();
  }
}

