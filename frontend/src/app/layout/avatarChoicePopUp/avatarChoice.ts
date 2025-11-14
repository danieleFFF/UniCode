import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-avatarChoice',
  standalone: true,
  imports: [],
  templateUrl: './avatarChoice.html',
  styleUrl: './avatarChoice.scss'
})

export class AvatarChoice {

  @Output() close = new EventEmitter<void>(); //cosi crea l'evento di chiusura

  public closePopUp(): void { //questa è la funzione che chiude il popUp
    this.close.emit();
  }

}
