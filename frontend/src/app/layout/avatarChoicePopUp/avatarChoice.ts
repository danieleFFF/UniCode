import {Component, EventEmitter, Output} from '@angular/core';
import {AvatarService} from '../../services/avatar.service';
import {CommonModule} from '@angular/common';

export interface Avatar {
  id: number;
  url_immagine: string;
}

@Component({
  selector: 'app-avatarChoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './avatarChoice.html',
  styleUrl: './avatarChoice.scss'
})

export class AvatarChoice {

  @Output() close = new EventEmitter<void>(); //cosi crea l'evento di chiusura

  public closePopUp(): void { //questa è la funzione che chiude il popUp
    this.close.emit();
  }

  // lista dove vanno a finire tutti gli avatar presi dal database
  allAvatars: Avatar[] = []

  // lista che contiene solo gli avatar che verranno visualizzati
  visibleAvatars: Avatar[] = []

  // variabile che contiene l'avatar selezionato (quello grande)'
  currentAvatar: string = ''

  // variabili per la paginazione
  avatarList: Avatar[] = [];
  pageIndex: number = 0; // dove 0 è la prima pagina e 1 la seconda pagina
  avatarNum: number = 6; //numero di avatar per pagina

  //costruttore che serve per chiamare il backend
  constructor(private avatarService: AvatarService) { }

  ngOnInit() : void { //all'avvio

    //chiamiamo il servizio per ottenere tutti gli avatar
    this.avatarService.getAvatars().subscribe( data =>{

      //riempe la lista con i dati ricevuti
      this.allAvatars = data;
      this.updateGrid();
      this.currentAvatar = this.allAvatars[0].url_immagine;


    });
  }


  updateGrid() {
    const start = this.pageIndex * this.avatarNum;
    const end = start + this.avatarNum;

    this.visibleAvatars = this.allAvatars.slice(start, end);
  }

  nextPage() {
    if((this.pageIndex + 1) * this.avatarNum < this.allAvatars.length){
      this.pageIndex++;
      this.updateGrid();
    }
  }

  prevPage() {
    if(this.pageIndex > 0){
      this.pageIndex--;
      this.updateGrid();
    }
  }

  selectAvatar(url:string) {
    this.currentAvatar = url;
  }

}
