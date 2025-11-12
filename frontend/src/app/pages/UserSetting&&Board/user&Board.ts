import { Component } from '@angular/core';
import {UserSettings} from '../../components/userSettings/userSettings';
import {Navbar} from '../../layout/navbar/navbar';

@Component(
  {
    selector: 'app-userBoard',
    imports: [UserSettings, Navbar],
    templateUrl: './user&Board.html',
    styleUrls: ['./user&Board.scss','../../components/userSettings/userSettings.scss','../../layout/navbar/navbar.scss']
  }
)

export class UserBoard {

}
