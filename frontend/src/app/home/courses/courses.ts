import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';


export interface Course {
  iconPath: string;
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
    {
      name: 'Python',
      iconPath: ""
    },
    {
      name: 'C++',
      iconPath: ""
    },
    {
      name: 'Java',
      iconPath: ""
    },
    { name: 'C' ,
    iconPath:""},
  ];
}
