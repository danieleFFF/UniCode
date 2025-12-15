import { Component , OnInit } from '@angular/core';
import {Navbar} from '../../layout/navbar/navbar';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Podium} from '../../components/rankingComponent/podiumComponent/podium';
import {ListRankingComponent} from '../../components/rankingComponent/listRankingComponent/listRanking';
import {User} from '../../models/user.model';
import {UserService} from '../../services/user.service';


@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [Navbar, CommonModule, FormsModule,Podium,ListRankingComponent],
  templateUrl: './ranking.html',
  styleUrls: ['ranking.scss','../../pages/home/home.scss', '../../layout/navbar/navbar.scss' , '../../components/rankingComponent/podiumComponent/podium.scss' , '../../components/rankingComponent/listRankingComponent/listRanking.scss' ]
})

export class Ranking implements OnInit{

  constructor(private userService: UserService) {}

  users : User[] = [];

  ngOnInit(): void {
    this.userService.getRanking(10).subscribe({
      next: (data) => {
        this.users = data;
      } , error: (err) => {
        console.error(err);
      }
    });
  }

}
