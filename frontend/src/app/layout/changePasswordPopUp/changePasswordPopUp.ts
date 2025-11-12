import { Component , Output , EventEmitter } from '@angular/core';

@Component({
  selector: 'app-changePasswordPopUp',
  standalone: true,
  imports: [],
  templateUrl: './changePasswordPopUp.html',
  styleUrls: ['./changePasswordPopUp.scss']
})
export class ChangePasswordPopUp {

  @Output() close = new EventEmitter<void>(); //cosi crea l'evento di chiusura

  public closePopUp(): void { //questa è la funzione che chiude il popUp
    this.close.emit();
  }

}
