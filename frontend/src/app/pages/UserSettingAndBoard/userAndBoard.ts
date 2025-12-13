import { Component, OnInit } from '@angular/core';
import { UserSettings } from '../../components/userSettings/userSettings';
import { Navbar } from '../../layout/navbar/navbar';
import { BoardComponent } from '../../components/boardComponent/boardComponent';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-userBoard',
  standalone: true,
  imports: [UserSettings, Navbar, BoardComponent, CommonModule],
  templateUrl: './userAndBoard.html',
  styleUrls: ['./userAndBoard.scss', '../../components/userSettings/userSettings.scss', '../../layout/navbar/navbar.scss',
    '../../components/boardComponent/boardComponent.scss']
})
export class UserAndBoard implements OnInit {

  currentUser: User | null = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (data: User) => {
        this.currentUser = data;
        console.log(this.currentUser)
      },
      error: (err) => {
        console.error('Error fetching user data in UserAndBoard', err);
      }
    });
  }
}
