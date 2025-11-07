import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DropdownMenu, DropdownItem } from '../dropdown-menu/dropdown-menu';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, DropdownMenu, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar {
  // Dati per il menu a tendina "Courses"
  courseItems: DropdownItem[] = [
    { name: 'Python', path: '/exercises/python', iconPath: 'assets/images/pythonimage.png' },
    { name: 'C++', path: '/exercises/cpp', iconPath: 'assets/images/cppimage.png' },
    { name: 'Java', path: '/exercises/java', iconPath: 'assets/images/javaimage.png' },
    { name: 'JavaScript', path: '/exercises/javascript', iconPath: 'assets/images/javascriptimage.png' },
    { name: 'HTML', path: '/exercises/html', iconPath: 'assets/images/htmlimage.png' },
    { name: 'SQL', path: '/exercises/sql', iconPath: 'assets/images/sqlimage.png' }
  ];

  // Dati per il menu a tendina "Exercises"
  exerciseItems: DropdownItem[] = [
    { name: 'All Challenges', path: '/exercises', iconPath: 'assets/images/exerciseimage.png' },
    { name: 'Easy', path: '/exercises/easy', iconPath: 'assets/images/rankingimage.png' },
    { name: 'Medium', path: '/exercises/medium', iconPath: 'assets/images/rankingimage.png' },
    { name: 'Hard', path: '/exercises/hard', iconPath: 'assets/images/rankingimage.png' }
  ];

  // Gestisce quale menu è visibile
  activeDropdown: 'courses' | 'exercises' | null = null;

  showDropdown(menu: 'courses' | 'exercises') {
    this.activeDropdown = menu;
  }

  hideDropdown() {
    this.activeDropdown = null;
  }
}
