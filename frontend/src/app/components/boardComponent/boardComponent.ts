import { Component } from '@angular/core';

@Component(
  {
    selector: 'app-board',
    standalone: true,
    templateUrl: './boardComponent.html',
    styleUrls: ['./boardComponent.scss']
  }
)

export class BoardComponent {

  //mettiamo in un array tutte le immagini con i relativi dati (cioe un booleano se sono stati sbloccati , un id , un nome e poi il path di dove si trovano nel progetto)
  //dividiamo in 2 array per le righe in cima e in basso

  topRowTrophies = [
    {
      id: '100exercise',
      description: '100 exercise completed',
      path: 'assets/images/trophies/thropies100excersise.png',
      unlocked: false //per ora non è stato sbloccato
    },

    {
      id: 'codeMaster',
      description: 'all the exercises solved',
      path: 'assets/images/trophies/trhopiesCodeMaster.png',
      unlocked: true
    },

    {
      id: 'firstRank',
      description: 'first place in the leaderboard',
      path: 'assets/images/trophies/thropies!rank.png',
      unlocked: false
    }
  ]
  bottomRowTrophies = [
    {
      id: '50exercise',
      description: '50 exercise completed',
      path: 'assets/images/trophies/thrtopies50excersise.png',
      unlocked: false,
    },

    {
      id: '20exercise',
      description: '20 exercise completed',
      path: 'assets/images/trophies/thropies20Excersize.png',
      unlocked: false
    },

    {
      id: 'pythonMaster',
      description: 'all python exercises solved',
      path: 'assets/images/trophies/thropiesPythonMaster.png',
      unlocked: false
    },

    {
      id: 'timeMaster',
      description: 'all exercises solved in under 10 minute',
      path: 'assets/images/trophies/thropiesTime.png',
      unlocked: false
    }
  ]

  constructor() {
  }

}
