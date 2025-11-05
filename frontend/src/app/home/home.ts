import { Component } from '@angular/core';
import { Navbar } from '../layout/navbar/navbar';
import { Hero } from './hero/hero';
import { Courses} from './courses/courses';
import {InfoCards} from './info-cards/info-cards';

@Component({
  selector: 'app-home',
  imports: [Navbar, Hero,Courses,InfoCards],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
}
