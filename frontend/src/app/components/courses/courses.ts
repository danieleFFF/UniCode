import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterLink} from '@angular/router';


export interface Course {
  iconPath: string;
  path: string;
  name: string;
}

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './courses.html',
  styleUrl: './courses.scss',
})
export class Courses{

  courses: Course[] = [
    {
      name: 'Python',
      path: '/courses/python',
      iconPath: "assets/images/pythonimage.png"
    },
    {
      name: 'C++',
      path: '/courses/cpp',
      iconPath: "assets/images/cppimage.png"
    },
    {
      name: 'Java',
      path: '/courses/java',
      iconPath: "assets/images/javaimage.png"
    },
    { name: 'Javascript',
      path: '/courses/javascript',
    iconPath:"assets/images/javascriptimage.png"
    },
    {
      name: 'html',
      path: '/courses/html',
      iconPath: "assets/images/htmlimage.png"
    }
    ,
    {
      name: 'sql',
      path: '/courses/sql',
      iconPath: "assets/images/sqlimage.png"
    }
  ];
}
