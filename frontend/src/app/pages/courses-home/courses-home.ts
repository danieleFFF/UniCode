import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterLink} from '@angular/router';
import { Navbar } from '../../layout/navbar/navbar';

export interface DropdownItem{
  name:string;
  path:string;
  iconPath:string;
}

@Component({
  selector:'app-courses-home',
  imports:[CommonModule,RouterLink,Navbar],
  templateUrl:'./courses-home.html',
  styleUrl:'./courses-home.scss',
})

export class CoursesHome{
  courseItems: DropdownItem[]=[
    {name:'Python',path:'/courses/python',iconPath:'assets/images/pythonimage.png'},
    {name:'C++',path:'/courses/cpp',iconPath:'assets/images/cppimage.png'},
    {name:'Java',path:'/courses/java',iconPath:'assets/images/javaimage.png'},
    {name:'JavaScript',path:'/courses/javascript',iconPath:'assets/images/javascriptimage.png'},
    {name:'HTML',path:'/courses/html',iconPath:'assets/images/htmlimage.png'},
    {name:'SQL',path:'/courses/sql',iconPath:'assets/images/sqlimage.png'}
  ];
}
