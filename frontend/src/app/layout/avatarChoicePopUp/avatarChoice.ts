import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { AvatarService, Avatar } from '../../services/avatar.service';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-avatarChoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './avatarChoice.html',
  styleUrls: ['./avatarChoice.scss']
})

export class AvatarChoice implements OnInit {

  @Output() close = new EventEmitter<void>(); //cosi crea l'evento di chiusura

  @Output() avatarUpdate = new EventEmitter<void>();

  @Input() initialAvatarId: number | null = null;

  public closePopUp(): void { //questa è la funzione che chiude il popUp
    this.close.emit();
  }

  // lista dove vanno a finire tutti gli avatar presi dal database
  allAvatars: Avatar[] = []

  // lista che contiene solo gli avatar che verranno visualizzati
  visibleAvatars: Avatar[] = []

  // variabile che contiene l'url dell'avatar selezionato (quello grande)'
  currentAvatar: string = ''
  //variabile che contiene l'id dell'avatar selezionato (quello grande)
  currentAvatarId: number | null = null;


  // variabili per la paginazione
  pageIndex: number = 0; // dove 0 è la prima pagina e 1 la seconda pagina
  avatarNum: number = 6; //numero di avatar per pagina

  //costruttore che serve per chiamare il backend
  constructor(private avatarService: AvatarService
    , private userService: UserService) { }

  ngOnInit(): void {
    this.avatarService.getAvatars().subscribe({
      next: (data) => {
        this.allAvatars = data;
        const avatarIniziale = this.allAvatars.find(avatar => avatar.id === this.initialAvatarId);

        if (avatarIniziale) {
          this.selectAvatar(avatarIniziale);
        } else if (this.allAvatars.length > 0) {

          this.selectAvatar(this.allAvatars[0]);
        }
        this.updateGrid();
      },
      error: (err) => console.error('Impossibile caricare avatar', err)
    });
  }


  updateGrid() {
    const start = this.pageIndex * this.avatarNum;
    const end = start + this.avatarNum;

    this.visibleAvatars = this.allAvatars.slice(start, end);
  }

  nextPage() {
    if ((this.pageIndex + 1) * this.avatarNum < this.allAvatars.length) {
      this.pageIndex++;
      this.updateGrid();
    }
  }

  prevPage() {
    if (this.pageIndex > 0) {
      this.pageIndex--;
      this.updateGrid();
    }
  }

  selectAvatar(avatar: Avatar) {
    this.currentAvatar = avatar.url_immagine;
    this.currentAvatarId = avatar.id;
  }
  save() {
    //dovuto mettere perche mi da problemi
    const idAvatarSelected = this.currentAvatarId;

    if (idAvatarSelected === null) {
      console.error("Nessun avatar selezionato");
      return;
    }

    this.userService.changeAvatar(idAvatarSelected).subscribe({
      next: () => {
        console.log("Avatar modificato con successo");
        this.userService.getProfile().subscribe();
        this.avatarUpdate.emit();
        this.closePopUp();
      },
      error: (err) => {
        console.error("Errore durante la modifica dell'avatar", err);
      }
    });
  }

}
