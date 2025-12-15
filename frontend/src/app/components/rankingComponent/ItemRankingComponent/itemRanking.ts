import {Component, Input} from '@angular/core';
import {User} from '../../../models/user.model';


@Component({
  selector: 'app-itemRanking',
  imports: [],
  templateUrl: './itemRanking.html',
  styleUrl: './itemRanking.scss',
})
export class ItemRanking {

  @Input() user!:User;
  @Input() position!:number;

}
