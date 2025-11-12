import { Component } from '@angular/core';
import {UserSettings} from '../../components/userSettings/userSettings';
import {Navbar} from '../../layout/navbar/navbar';
import {BoardComponent} from '../../components/boardComponent/boardComponent';

@Component(
  {
    selector: 'app-userBoard',
    imports: [UserSettings, Navbar , BoardComponent],
    templateUrl: './user&Board.html',
    styleUrls: ['./user&Board.scss','../../components/userSettings/userSettings.scss','../../layout/navbar/navbar.scss',
      '../home/home.scss','../../components/boardComponent/boardComponent.scss']
  }
)

export class UserBoard {

}
