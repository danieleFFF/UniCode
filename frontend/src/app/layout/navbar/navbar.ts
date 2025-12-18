import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DropdownMenu, DropdownItem } from '../dropdown-menu/dropdown-menu';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import {User} from '../../models/user.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, DropdownMenu, CommonModule],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.scss', '../../components/userSettings/userSettings.scss']
})
export class Navbar implements OnInit {
  user: User | null = null;

  courseItems: DropdownItem[] = [
    { name: 'Python', path: '/courses/python', iconPath: 'assets/images/pythonimage.png' },
    { name: 'C++', path: '/courses/cpp', iconPath: 'assets/images/cppimage.png' },
    { name: 'Java', path: '/courses/java', iconPath: 'assets/images/javaimage.png' },
    { name: 'JavaScript', path: '/courses/javascript', iconPath: 'assets/images/javascriptimage.png' },
    { name: 'HTML', path: '/courses/html', iconPath: 'assets/images/htmlimage.png' },
    { name: 'SQL', path: '/courses/sql', iconPath: 'assets/images/sqlimage.png' }
  ];

  exerciseItems: DropdownItem[] = [
    { name: 'Mix Challenges', path: '/exercises', iconPath: 'assets/images/exerciseimage.png' },
    { name: 'Easy', path: '/exercises/easy', iconPath: 'assets/images/rankingimage.png' },
    { name: 'Medium', path: '/exercises/medium', iconPath: 'assets/images/rankingimage.png' },
    { name: 'Hard', path: '/exercises/hard', iconPath: 'assets/images/rankingimage.png' }
  ];
  activeDropdown:'courses'|'exercises'|null=null;
  constructor(
    public authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: () => {
        // Non loggato, nessun problema
        this.user = null;
      }
    });
  }
  showDropdown(menu:'courses'|'exercises'){
    this.activeDropdown=menu;
  }
  hideDropdown(){
    this.activeDropdown=null;
  }
}
