import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface CourseContent{
  key: string;
  title: string;
  iconPath: string;
  description: string;
  topics: { name: string; difficulty: 'Easy' | 'Medium' | 'Hard' }[];
}
@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html',
  styleUrl: './courses.scss'
})
export class Courses implements OnInit{
  private courseData: Map<string, CourseContent>=new Map([
    ['python', {
      key: 'python',
      title: 'Python Course',
      iconPath: 'assets/images/pythonimage.png',
      description: 'Learn Python, a versatile language used in web development, data science, and automation. Start from the basics and move to advanced topics.',
      topics: [
        { name: 'Syntax and Variables', difficulty: 'Easy' },
        { name: 'Data Structures (Lists, Dictionaries)', difficulty: 'Easy' },
        { name: 'Functions and Modules', difficulty: 'Medium' },
        { name: 'Object-Oriented Programming', difficulty: 'Hard' }
      ]
    }],
    ['java', {
      key: 'java',
      title: 'Java Course',
      iconPath: 'assets/images/javaimage.png',
      description: 'Java is a robust, object-oriented language used for enterprise applications, Android development, and large-scale backend systems.',
      topics: [
        { name: 'JVM, Syntax, and Primitive Types', difficulty: 'Easy' },
        { name: 'Classes and Objects', difficulty: 'Medium' },
        { name: 'Inheritance and Polymorphism', difficulty: 'Medium' },
        { name: 'Collections Framework', difficulty: 'Hard' }
      ]
    }],
    ['cpp', {
      key: 'cpp',
      title: 'C++ Course',
      iconPath: 'assets/images/cppimage.png',
      description: 'Master C++, a powerful language used for operating systems, game development, and high-performance software.',
      topics: [
        { name: 'Pointers and Memory Management', difficulty: 'Hard' },
        { name: 'Classes and Templates', difficulty: 'Medium' },
        { name: 'Standard Template Library (STL)', difficulty: 'Hard' }
      ]
    }],
    ['html', {
      key: 'html',
      title: 'HTML Course',
      iconPath: 'assets/images/htmlimage.png',
      description: 'Learn the basics of HTML5 to structure web pages. This is the starting point for every web developer.',
      topics: [
        { name: 'Tags and Attributes', difficulty: 'Easy' },
        { name: 'Forms and Inputs', difficulty: 'Easy' },
        { name: 'Semantic HTML', difficulty: 'Medium' }
      ]
    }],
    ['javascript', {
      key: 'javascript',
      title: 'JavaScript Course',
      iconPath: 'assets/images/javascriptimage.png',
      description: 'Learn the basics of JavaScript, a programming language used to add interactivity and dynamic features to websites.',
      topics: [
        { name: 'Variables, Data Types, and Operators', difficulty: 'Easy' },
        { name: 'Event Handling', difficulty: 'Medium' },
        { name: 'Asynchronous Programming', difficulty: 'Hard' }
      ]
    }],
    ['sql', {
      key: 'sql',
      title: 'SQL Course',
      iconPath: 'assets/images/sqlimage.png',
      description: 'Learn the basics of the SQL language, a query language used to manage and manipulate data in a database.',
      topics: [
        { name: 'Selection and Joins', difficulty: 'Easy' },
        { name: 'Functions and Aggregations', difficulty: 'Medium' },
        { name: 'Tables and Views', difficulty: 'Medium' }
      ]
    }]
  ]);
  public currentCourse: CourseContent | undefined;
  public errorMessage: string | null = null;
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const languageKey = params.get('language');

      if (languageKey) {
        this.loadCourseContent(languageKey);
      } else {
        this.router.navigate(['/']);
      }
    });
  }
  private loadCourseContent(key: string): void {
    const content = this.courseData.get(key);
    this.currentCourse = content;
    this.errorMessage = null;
  }
  public getXpColor(difficulty: 'Easy' | 'Medium' | 'Hard'): string {
    switch (difficulty) {
      case 'Easy': return '#6eff7f';
      case 'Medium': return '#ffc66e';
      case 'Hard': return '#ff6e6e';
      default: return '#aaaaaa';
    }
  }
}
