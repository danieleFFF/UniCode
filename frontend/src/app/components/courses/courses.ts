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
      iconPath: "assets/images/pythonimage.png"
    },
    {
      name: 'C++',
      iconPath: "assets/images/cppimage.png"
    },
    {
      name: 'Java',
      iconPath: "assets/images/javaimage.png"
    },
    { name: 'Javascript' ,
    iconPath:"assets/images/javascriptimage.png"
    },
    {
      name: 'html',
      iconPath: "assets/images/htmlimage.png"
    }
    ,
    {
      name: 'sql',
      iconPath: "assets/images/sqlimage.png"
    }
  ];
}
