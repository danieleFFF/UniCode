import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

export interface TopicFromDB {
  id: number;
  idLanguage: number;
  title: string;
  introduction: string;
  codeExample: string;
  keyConcepts: string;
  urlVideo: string;
  difficulty: 'Easy' | 'Medium' | 'Hard';
}

export interface CourseInfo {
  key: string;
  title: string;
  iconPath: string;
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class CourseDataService {

  private apiUrl = 'http://localhost:8080/api/topics';

  private languageIdMap: { [key: string]: number } = {
    'python': 1,
    'java': 2,
    'cpp': 3,
    'html': 4,
    'javascript': 5,
    'sql': 6
  };

  private staticCourseInfo: Map<string, CourseInfo> = new Map([
    ['python', { key: 'python', title: 'Python Course', iconPath: 'assets/images/pythonimage.png', description: 'Learn Python from basics to advanced.' }],
    ['java', { key: 'java', title: 'Java Course', iconPath: 'assets/images/javaimage.png', description: 'Java is a robust, object-oriented language.' }],
    ['cpp', { key: 'cpp', title: 'C++ Course', iconPath: 'assets/images/cppimage.png', description: 'Master C++ and memory management.' }],
    ['html', { key: 'html', title: 'HTML Course', iconPath: 'assets/images/htmlimage.png', description: 'Structure web pages with HTML5.' }],
    ['javascript', { key: 'javascript', title: 'JavaScript Course', iconPath: 'assets/images/javascriptimage.png', description: 'Add interactivity with JS.' }],
    ['sql', { key: 'sql', title: 'SQL Course', iconPath: 'assets/images/sqlimage.png', description: 'Master database queries.' }]
  ]);

  constructor(private http: HttpClient) { }

  public getCourseInfo(key: string): CourseInfo | undefined {
    return this.staticCourseInfo.get(key);
  }

  public getTopics(languageKey: string): Observable<TopicFromDB[]> {
    const languageId = this.languageIdMap[languageKey];

    if (!languageId) {
      console.error();
      return of([]);
    }

    return this.http.get<TopicFromDB[]>(
      `${this.apiUrl}/language/${languageId}`,
      { withCredentials: true }
    );
  }

  public getTopic(languageKey: string, topicIndex: number): Observable<TopicFromDB | undefined> {
    const languageId = this.languageIdMap[languageKey];

    if (!languageId) {
      return of(undefined);
    }

    return this.http.get<TopicFromDB[]>(
      `${this.apiUrl}/language/${languageId}`,
      { withCredentials: true }
    ).pipe(
      map(topics => {
        if (topics && topics[topicIndex]) {
          return topics[topicIndex];
        }
        return undefined;
      })
    );
  }

  public getXpColor(difficulty: 'Easy' | 'Medium' | 'Hard' | undefined): string {
    if (!difficulty) return '#aaaaaa';
    switch (difficulty) {
      case 'Easy': return '#6eff7f';
      case 'Medium': return '#ffc66e';
      case 'Hard': return '#ff6e6e';
      default: return '#aaaaaa';
    }
  }
}
