import { Component } from '@angular/core';
import {Navbar} from '../../layout/navbar/navbar';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Podium} from '../../components/rankingComponent/podiumComponent/podium';
import {ListRankingComponent} from '../../components/rankingComponent/listRankingComponent/listRanking';


@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [Navbar, CommonModule, FormsModule,Podium,ListRankingComponent],
  templateUrl: './ranking.html',
  styleUrls: ['ranking.scss','../../pages/home/home.scss', '../../layout/navbar/navbar.scss' , '../../components/rankingComponent/podiumComponent/podium.scss' , '../../components/rankingComponent/listRankingComponent/listRanking.scss' ]
})

export class Ranking {

}
