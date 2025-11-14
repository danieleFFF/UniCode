import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CourseDataService, CourseContent } from '../../services/course-data';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
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
    const content = this.courseService.getCourse(key);
    this.currentCourse = content;
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
