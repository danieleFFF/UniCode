import { Component } from '@angular/core';
import { Navbar } from '../../layout/navbar/navbar';
import { Hero } from '../../components/hero/hero';
import { Courses} from '../../components/courses/courses';
import {InfoCards} from '../../components/info-cards/info-cards';

@Component({
  selector: 'app-home',
  imports: [Navbar, Hero,Courses,InfoCards],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
}
