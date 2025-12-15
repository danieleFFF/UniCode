import {Component, Input} from '@angular/core';
import {User} from '../../../models/user.model';

@Component(
  {
    selector: 'app-podium-item',
    templateUrl: './podiumItem.html',
    styleUrls: ['./podiumItem.scss']
  }
)

export class PodiumItem {
  @Input() user!: User;
  @Input() position!: number;
}
