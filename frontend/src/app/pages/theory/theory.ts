import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CourseDataService } from '../../services/course-data';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import {Navbar} from "../../layout/navbar/navbar";

@Component({
  selector: 'app-theory',
  standalone: true,
    imports: [CommonModule, Navbar],
  templateUrl: './theory.html',
  styleUrl: './theory.scss'
})
export class Theory implements OnInit {
  public topicTitle: string | undefined;
  public topicDifficulty: 'Easy' | 'Medium' | 'Hard' | undefined;
  public errorMessage: string | null = null;
  public safeVideoUrl: SafeResourceUrl | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public courseService: CourseDataService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const languageKey = params.get('language');
      const topicIndexStr = params.get('topicIndex');
      if (languageKey && topicIndexStr) {
        const topicIndex = +topicIndexStr;
        this.loadTopicContent(languageKey, topicIndex);
      } else {
        this.router.navigate(['/']);
      }
    });
  }
  private loadTopicContent(key: string, index: number): void {
    const topic = this.courseService.getTopic(key, index);
    if (topic) {
      this.topicTitle = topic.name;
      this.topicDifficulty = topic.difficulty;
      this.errorMessage = null;
      if (topic.videoId) {
        const videoUrl = 'https://www.youtube.com/embed/' + topic.videoId;
        this.safeVideoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(videoUrl);
      } else {
        this.safeVideoUrl = null;
      }
    } else {
      this.errorMessage = "Topic not found.";
      this.topicTitle = undefined;
      this.topicDifficulty = undefined;
    }
  }
}
