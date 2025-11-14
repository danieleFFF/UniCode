import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DropdownMenu, DropdownItem } from '../dropdown-menu/dropdown-menu';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, DropdownMenu, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar {
  isLoggedIn$: Observable<boolean>;
  // Dati per il menu a tendina "Courses"
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
  constructor(private authService:AuthService){
    this.isLoggedIn$=this.authService.isLoggedIn$;
  }
  showDropdown(menu:'courses'|'exercises'){
    this.activeDropdown=menu;
  }
  hideDropdown(){
    this.activeDropdown=null;
  }
  logout(): void {
    this.authService.logout();
  }
}
