import { Component } from '@angular/core';
import { Header } from '../layout/header/header';
import { Hero } from './hero/hero';
import { Courses} from './courses/courses';

@Component({
  selector: 'app-home',
  imports: [Header, Hero,Courses],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
}
