import { Component , Output , EventEmitter } from '@angular/core';

@Component({
  selector: 'app-deleteAccountPopUp',
  standalone: true,
  imports: [],
  templateUrl: './deleteAccountPopUp.html',
  styleUrl: './deleteAccountPopUp.scss'
})

export class DeleteAccountPopUp {
  @Output() close = new EventEmitter<void>();

  public closePopUp(): void {
    this.close.emit();
  }

}
