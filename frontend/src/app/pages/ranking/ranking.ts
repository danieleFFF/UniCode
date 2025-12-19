import { Component , OnInit } from '@angular/core';
import {Navbar} from '../../layout/navbar/navbar';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Podium} from '../../components/rankingComponent/podiumComponent/podium';
import {ListRankingComponent} from '../../components/rankingComponent/listRankingComponent/listRanking';
import {User} from '../../models/user.model';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [Navbar, CommonModule, FormsModule,Podium,ListRankingComponent],
  templateUrl: './ranking.html',
  styleUrl: 'ranking.scss'}
)

export class Ranking implements OnInit{

  constructor(private userService: UserService, private router: Router) { }

  users : User[] = [];

  ngOnInit(): void {
    this.userService.currentUser$.subscribe({
      next: (user) => {
        if (this.userService.authChecked && !user) {
          this.router.navigate(['/login'], { replaceUrl: true });
        }
      }
    });

    if (!this.userService.getCurrentUser()) {
      this.userService.getProfile().subscribe({
        error: () => this.router.navigate(['/login'])
      });
    }

    this.userService.getRanking(10).subscribe({
      next: (data) => {
        this.users = data;
      } , error: (err) => {
        console.error(err);
      }
    });
  }

}
