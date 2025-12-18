import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {CourseContent, CourseDataService} from '../../services/course-data';
import {Navbar} from '../../layout/navbar/navbar';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './courses.html',
  styleUrl: './courses.scss'
})
export class Courses implements OnInit{
  public currentCourse: CourseContent | undefined;
  public errorMessage: string | null = null;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public courseService: CourseDataService
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
    this.currentCourse = this.courseService.getCourse(key);
    this.errorMessage = null;
  }
  public goToTopic(topicIndex: number): void {
    if (this.currentCourse) {
      this.router.navigate([
        '/courses',
        this.currentCourse.key,
        'topic',
        topicIndex
      ]);
    }
  }
}
