import { Component, Input } from '@angular/core';
import { ItemRanking } from '../ItemRankingComponent/itemRanking';
import { User } from '../../../models/user.model';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-listRanking',
  imports: [ItemRanking, CommonModule],
  templateUrl: './listRanking.html',
  styleUrls: ['./listRanking.scss', '../ItemRankingComponent/itemRanking.scss']
})
export class ListRankingComponent {
  @Input() users: User[] = [];
}
