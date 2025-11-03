import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';


export interface Course {
  name: string;
}

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html',
  styleUrl: './courses.scss',
})
export class Courses{

  courses: Course[] = [
    { name: 'Python' },
    { name: 'C++' },
    { name: 'Java' },
    { name: 'C' },
  ];
}
