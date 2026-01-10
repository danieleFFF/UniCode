import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../layout/navbar/navbar';
import { CourseDataService, CourseInfo, TopicFromDB } from '../../services/course-data';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './courses.html',
  styleUrl: './courses.scss'
})
export class Courses implements OnInit {

  public courseInfo: CourseInfo | undefined;
  public topicList: TopicFromDB[] = [];
  public errorMessage: string | null = null;
  public languageKey: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public courseService: CourseDataService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const languageKey = params.get('language');
      if (languageKey) {
        this.languageKey = languageKey;
        this.loadCourseContent(languageKey);
      } else {
        this.router.navigate(['/']);
      }
    });
  }

  private loadCourseContent(key: string): void {
    this.courseInfo = this.courseService.getCourseInfo(key);
    if (!this.courseInfo) {
      this.errorMessage = "Course not found";
      return;
    }
    this.courseService.getTopics(key).subscribe({
      next: (data) => {
        this.topicList = data;
        this.errorMessage = null;
      },
      error: (err) => {
        console.error('errore nel caricamento dei topic:', err);
      }
    });
  }

  public goToTopic(topicIndex: number): void {
    if (this.languageKey) {
      this.router.navigate([
        '/courses',
        this.languageKey,
        'topic',
        topicIndex
      ]);
    }
  }
}
