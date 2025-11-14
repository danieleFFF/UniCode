import { Component } from '@angular/core';
import {ChangePasswordPopUp} from '../../layout/changePasswordPopUp/changePasswordPopUp';
import {DeleteAccountPopUp} from '../../layout/deleteAccountPopUp/deleteAccountPopUp';
import {CommonModule} from '@angular/common';//pacchetto che permette l'utilizzo di *ngif ecc ecc
import {AvatarChoice} from '../../layout/avatarChoicePopUp/avatarChoice';

@Component({
  selector: 'app-userSettings',
  imports: [
    ChangePasswordPopUp,
    DeleteAccountPopUp,
    CommonModule,
    AvatarChoice
  ],
  templateUrl: './userSettings.html',
  styleUrl: './userSettings.scss',
})
export class UserSettings {

  //queste 2 variabili sono per la visualizzazione dei pop up , impostate a false per iniziare
  public showChangePasswordPopUp = false;
  public showDeleteAccountPopUp = false;
  public showAvatarChoicePopUp = false;

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


}
