import { Component } from '@angular/core';
import { Navbar } from '../layout/navbar/navbar';
import { Hero } from './hero/hero';
import { Courses} from './courses/courses';

@Component({
  selector: 'app-home',
  imports: [Navbar, Hero,Courses],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
}
