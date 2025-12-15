import { Component, Input } from '@angular/core';
import { PodiumItem } from '../podiumItem/podiumItem';
import { CommonModule } from '@angular/common';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-podium',
  imports: [PodiumItem, CommonModule],
  templateUrl: './podium.html',
  styleUrls: ['./podium.scss', '../podiumItem/podiumItem.scss']
})

export class Podium {
  @Input() users: User[] = [];
}
