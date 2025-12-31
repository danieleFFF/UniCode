import { Injectable } from '@angular/core';

export interface CourseContent {
  key: string;
  title: string;
  iconPath: string;
  description: string;
  topics: { name: string; difficulty: 'Easy' | 'Medium' | 'Hard'; videoId?: string;}[];
}

export type CourseTopic = { name: string; difficulty: 'Easy' | 'Medium' | 'Hard', videoId?: string;};

@Injectable({
  providedIn: 'root'
})
export class CourseDataService {
  private courseData: Map<string, CourseContent> = new Map([
    ['python', {
      key: 'python',
      title: 'Python Course',
      iconPath: 'assets/images/pythonimage.png',
      description: 'Learn Python, a versatile language used in web development, data science, and automation. Start from the basics and move to advanced topics.',
      topics: [
        { name: 'Syntax and Variables', difficulty: 'Easy', videoId: 'LKFrQXaoSMQ'},
        { name: 'Data Structures (Lists, Dictionaries)', difficulty: 'Easy', videoId: 'R-HLU9Fl5ug' },
        { name: 'Functions and Modules', difficulty: 'Medium', videoId: '89cGQjB5R4M' },
        { name: 'Object-Oriented Programming', difficulty: 'Hard', videoId: 'q2SGW2VgwAM' }
      ]
    }],
    ['java', {
      key: 'java',
      title: 'Java Course',
      iconPath: 'assets/images/javaimage.png',
      description: 'Java is a robust, object-oriented language used for enterprise applications, Android development, and large-scale backend systems.',
      topics: [
        { name: 'JVM, Syntax, and Primitive Types', difficulty: 'Easy', videoId: 'OmcFVHpb0v0' },
        { name: 'Classes and Objects', difficulty: 'Medium', videoId: 'IUqKuGNasdM' },
        { name: 'Inheritance and Polymorphism', difficulty: 'Medium', videoId: 'gc216ndOrb4' },
        { name: 'Collections Framework', difficulty: 'Hard', videoId: 'viTHc_4XfCA' }
      ]
    }],
    ['cpp', {
      key: 'cpp',
      title: 'C++ Course',
      iconPath: 'assets/images/cppimage.png',
      description: 'Master C++, a powerful language used for operating systems, game development, and high-performance software.',
      topics: [
        { name: 'Classes and Templates', difficulty: 'Medium', videoId: 'mQqzP9EWu58'},
        { name: 'Pointers and Memory Management', difficulty: 'Hard', videoId: 'slzcWKWCMBg' },
        { name: 'Standard Template Library (STL)', difficulty: 'Hard', videoId: '_NlRcT5gWpo' }
      ]
    }],
    ['html', {
      key: 'html',
      title: 'HTML Course',
      iconPath: 'assets/images/htmlimage.png',
      description: 'Learn the basics of HTML5 to structure web pages. This is the starting point for every web developer.',
      topics: [
        { name: 'Tags and Attributes', difficulty: 'Easy', videoId: 'yMX901oVtn8' },
        { name: 'Forms and Inputs', difficulty: 'Easy', videoId: 'H9zdfZrFUp0' },
        { name: 'Semantic HTML', difficulty: 'Medium', videoId: 'kX3TfdUqpuU' }
      ]
    }],
    ['javascript', {
      key: 'javascript',
      title: 'JavaScript Course',
      iconPath: 'assets/images/javascriptimage.png',
      description: 'Learn the basics of JavaScript, a programming language used to add interactivity and dynamic features to websites.',
      topics: [
        { name: 'Variables, Data Types, and Operators', difficulty: 'Easy', videoId: 'g23MWKirn1c' },
        { name: 'Event Handling', difficulty: 'Medium', videoId: '_BVkOvpyRI0' },
        { name: 'Asynchronous Programming', difficulty: 'Hard', videoId: '670f71LTWpM' }
      ]
    }],
    ['sql', {
      key: 'sql',
      title: 'SQL Course',
      iconPath: 'assets/images/sqlimage.png',
      description: 'Learn the basics of the SQL language, a query language used to manage and manipulate data in a database.',
      topics: [
        { name: 'Selection and Joins', difficulty: 'Easy', videoId: 'G3lJAxg1cy8' },
        { name: 'Functions and Aggregations', difficulty: 'Medium', videoId: 'jcoJuc5e3RE' },
        { name: 'Tables and Views', difficulty: 'Medium', videoId: 'eumDqVqaCT4' }
      ]
    }]
  ]);
  constructor() { }
  public getCourse(key: string): CourseContent | undefined {
    return this.courseData.get(key);
  }
  public getTopic(courseKey: string, topicIndex: number): CourseTopic | undefined {
    const course = this.courseData.get(courseKey);
    if (course && course.topics && course.topics[topicIndex]) {
      return course.topics[topicIndex];
    }
    return undefined;
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
