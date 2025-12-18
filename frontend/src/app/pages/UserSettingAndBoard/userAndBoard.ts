import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { UserSettings } from '../../components/userSettings/userSettings';
import { Navbar } from '../../layout/navbar/navbar';
import { BoardComponent } from '../../components/boardComponent/boardComponent';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-userBoard',
  standalone: true,
  imports: [UserSettings, Navbar, BoardComponent, CommonModule],
  templateUrl: './userAndBoard.html',
  styleUrls: ['./userAndBoard.scss']
})
export class UserAndBoard implements OnInit {

  currentUser: User | null = null;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }
    this.userService.getProfile().subscribe({
      next: (data: User) => {
        this.currentUser = data;
      },
      error: (err) => {
        this.router.navigate(['/login']);
      }
    });
  }
}
