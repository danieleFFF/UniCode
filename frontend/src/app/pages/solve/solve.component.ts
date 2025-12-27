import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { ActivatedRoute } from '@angular/router'
import { HttpClient, HttpClientModule } from '@angular/common/http'
import { Navbar } from '../../layout/navbar/navbar'
import { UserService } from '../../services/user.service';
import { environment } from '../../../environments/environment';

interface CachedResult {
  showResults: boolean;
  testResults: any[];
  allPassed: boolean;
  consoleOutput: string;
  programOutput: string;
}

@Component({
  selector: 'app-solve',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, Navbar],
  templateUrl: './solve.component.html',
  styleUrls: ['./solve.component.scss']
})
export class SolveComponent implements OnInit, OnDestroy {
  @ViewChild('previewFrame') previewFrame!: ElementRef<HTMLIFrameElement>;

  exercise: any
  userCode = ''
  previewHtml = ''
  testResults: any[] = []
  showResults = false
  allPassed = false
  selectedLanguage = ''
  timer: any
  timeLeft = 0
  formattedTime = ''
  currentPoints = 0
  consoleOutput = ''
  programOutput = ''
  detailedOutput: string[] = []
  startTime = 0
  earnedPoints = 0
  isRunning = false
  alreadyCompleted = false
  isDemoMode = false
  solutionDemo = ''
  showSolution = false
  private testCache: Map<string, CachedResult> = new Map();
  private readonly MAX_CACHE_SIZE = 50;
  private readonly DEMO_LANGUAGES = ['HTML'];
  constructor(private route: ActivatedRoute, private http: HttpClient, private userService: UserService) { }

  onCodeChange(code: string): void {
  }

  validateHtml(): void {
    if (!this.userCode || this.userCode.trim() === '') {
      this.consoleOutput = 'Please write some HTML code first';
      this.showResults = true;
      return;
    }

    this.isRunning = true;
    this.showResults = false;
    this.testResults = [];

    const requirements = this.getHtmlRequirements();
    const parser = new DOMParser();
    const doc = parser.parseFromString(this.userCode.replace(/\\n/g, '\n'), 'text/html');

    let passedCount = 0;

    requirements.forEach((req, index) => {
      const elements = doc.querySelectorAll(req.selector);
      const passed = elements.length >= req.minCount;

      if (passed) passedCount++;

      this.testResults.push({
        name: `Requirement ${index + 1}`,
        message: passed ? req.successMsg : req.failMsg,
        passed
      });
    });

    this.allPassed = passedCount === requirements.length;
    this.consoleOutput = `${passedCount}/${requirements.length} requirements met`;
    this.showResults = true;
    this.isRunning = false;

    if (this.allPassed) {
      this.submitSolution();
    }
  }

  private getHtmlRequirements(): { selector: string; minCount: number; successMsg: string; failMsg: string }[] {
    const title = this.exercise?.title || '';

    if (title.includes('Basic Page Structure')) {
      return [
        { selector: 'header', minCount: 1, successMsg: '<header> element found', failMsg: 'Missing <header> element' },
        { selector: 'main', minCount: 1, successMsg: '<main> element found', failMsg: 'Missing <main> element' },
        { selector: 'footer', minCount: 1, successMsg: '<footer> element found', failMsg: 'Missing <footer> element' },
        { selector: 'h1', minCount: 1, successMsg: '<h1> heading found', failMsg: 'Missing <h1> heading' }
      ];
    } else if (title.includes('Contact Form')) {
      return [
        { selector: 'form', minCount: 1, successMsg: '<form> element found', failMsg: 'Missing <form> element' },
        { selector: 'label', minCount: 3, successMsg: 'At least 3 labels found', failMsg: 'Need at least 3 <label> elements' },
        { selector: 'input[type="text"], input[type="email"]', minCount: 2, successMsg: 'Input fields found', failMsg: 'Missing input fields' },
        { selector: 'textarea', minCount: 1, successMsg: '<textarea> found', failMsg: 'Missing <textarea> for message' },
        { selector: 'button[type="submit"], input[type="submit"]', minCount: 1, successMsg: 'Submit button found', failMsg: 'Missing submit button' }
      ];
    } else if (title.includes('Accessible Navigation')) {
      return [
        { selector: 'nav', minCount: 1, successMsg: '<nav> element found', failMsg: 'Missing <nav> element' },
        { selector: 'nav ul, nav ol', minCount: 1, successMsg: 'Navigation list found', failMsg: 'Missing list in nav' },
        { selector: 'nav a', minCount: 3, successMsg: 'At least 3 nav links found', failMsg: 'Need at least 3 navigation links' },
        { selector: '[role], [aria-label], [aria-current]', minCount: 1, successMsg: 'ARIA attributes found', failMsg: 'Missing ARIA accessibility attributes' },
        { selector: 'a[href="#main"], .skip-link', minCount: 1, successMsg: 'Skip link found', failMsg: 'Missing skip-to-content link' }
      ];
    }

    return [
      { selector: 'html', minCount: 1, successMsg: 'Valid HTML document', failMsg: 'Invalid HTML structure' }
    ];
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Tab') {
      event.preventDefault();
      const textarea = event.target as HTMLTextAreaElement;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;
      const indent = '  ';

      this.userCode = this.userCode.substring(0, start) + indent + this.userCode.substring(end);

      setTimeout(() => {
        textarea.selectionStart = textarea.selectionEnd = start + indent.length;
      }, 0);
    }
  }

  ngOnInit(): void {
    this.resetState();
    const id = this.route.snapshot.paramMap.get('id');

    this.http.get(`${environment.apiUrl}/exercises/${id}`, { withCredentials: true }).subscribe((data: any) => {
      this.exercise = data;
      this.selectedLanguage = data.languageName || 'Python';
      this.currentPoints = this.exercise.points || 100;
      this.isDemoMode = this.DEMO_LANGUAGES.includes(this.selectedLanguage);
      this.solutionDemo = data.solutionDemo || '';

      if (this.isDemoMode) {
        this.formattedTime = '--:--';
      } else {
        this.timeLeft = 600;
        this.startTime = Date.now();
        this.startTimer();
        this.checkIfAlreadyCompleted();
      }
    });
  }

  checkIfAlreadyCompleted(): void {
    const user = this.userService.getCurrentUser();
    if (!user) return;

    this.http.get(`${environment.apiUrl}/submissions/check/${user.id}/${this.exercise.id}`, { withCredentials: true }).subscribe({
      next: (response: any) => {
        this.alreadyCompleted = response.completed;
        if (this.alreadyCompleted) {
          this.consoleOutput = `You have already completed this exercise and earned ${response.pointsEarned} points!`;
        }
      },
      error: (err) => console.error('Error checking completion:', err)
    });
  }

  resetState(): void {
    this.stopTimer();
    this.userCode = '';
    this.testResults = [];
    this.showResults = false;
    this.allPassed = false;
    this.consoleOutput = '';
    this.programOutput = '';
    this.detailedOutput = [];
    this.startTime = 0;
    this.earnedPoints = 0;
    this.isRunning = false;
    this.timeLeft = 600;
    this.alreadyCompleted = false;
  }
  startTimer(): void {
    this.updateFormattedTime()
    this.timer = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--
        this.updateFormattedTime()
      } else {
        clearInterval(this.timer)
      }
    }, 1000)
  }

  stopTimer(): void {
    if (this.timer) {
      clearInterval(this.timer);
      this.timer = null;
    }
  }

  updateFormattedTime(): void {
    const minutes = Math.floor(this.timeLeft / 60)
    const seconds = this.timeLeft % 60
    this.formattedTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`
  }

  calculatePoints(): number {
    const elapsedSeconds = Math.floor((Date.now() - this.startTime) / 1000);
    const maxPoints = this.exercise.points || 100;
    let difficultyMultiplier = 1.0;

    switch (this.exercise.difficulty) {
      case 'Easy':
        difficultyMultiplier = 1.0;
        break;
      case 'Medium':
        difficultyMultiplier = 1.5;
        break;
      case 'Hard':
        difficultyMultiplier = 2.0;
        break;
    }

    const timePenalty = Math.floor(elapsedSeconds / 30);
    const basePoints = Math.floor(maxPoints * difficultyMultiplier);
    const finalPoints = Math.max(Math.floor(maxPoints * 0.3), basePoints - timePenalty);
    return finalPoints;
  }

  private addToCache(key: string, value: CachedResult): void {
    if (this.testCache.size >= this.MAX_CACHE_SIZE) {
      const firstKey = this.testCache.keys().next().value;
      if (typeof firstKey === "string") {
        this.testCache.delete(firstKey);
      }
    }
    this.testCache.set(key, value);
  }

  runTests(): void {
    if (!this.exercise || !this.exercise.id) {
      console.warn('No exercise loaded');
      return;
    }

    if (!this.userCode || this.userCode.trim() === '') {
      this.consoleOutput = 'Please write some code first';
      this.showResults = true;
      return;
    }

    if (this.isRunning) {
      return;
    }

    const cacheKey = `${this.exercise.id}_${this.userCode}`;

    if (this.testCache.has(cacheKey)) {
      const cached = this.testCache.get(cacheKey)!;
      this.showResults = cached.showResults;
      this.testResults = cached.testResults;
      this.allPassed = cached.allPassed;
      this.consoleOutput = cached.consoleOutput;
      this.programOutput = cached.programOutput;
      return;
    }

    this.isRunning = true;
    this.showResults = false;
    this.testResults = [];
    this.consoleOutput = 'Running tests...';
    this.programOutput = '';
    this.detailedOutput = [];
    this.allPassed = false;
    this.earnedPoints = 0;

    const languageMap: { [key: string]: number } = {
      'python': 71,
      'c++': 54,
      'java': 62,
      'javascript': 63,
      'sql': 82
    };

    const lang = (this.selectedLanguage || 'python').toLowerCase();
    const langId = languageMap[lang];

    if (!langId) {
      this.consoleOutput = `Language ${this.selectedLanguage} not supported`;
      this.showResults = true;
      this.isRunning = false;
      return;
    }

    this.http.get(`${environment.apiUrl}/exercises/${this.exercise.id}/tests`, { withCredentials: true }).subscribe({
      next: (tests: any) => {
        if (!tests || tests.length === 0) {
          this.consoleOutput = 'No test cases found';
          this.showResults = true;
          this.isRunning = false;
          return;
        }

        this.showResults = true;
        let completed = 0;
        let passedCount = 0;

        tests.forEach((test: any, index: number) => {
          const payload = {
            language_id: langId,
            code: this.userCode,
            stdin: test.input || ''
          };

          this.http.post(`${environment.apiUrl}/exercises/${this.exercise.id}/run`, payload, { withCredentials: true }).subscribe({
            next: (res: any) => {
              const normalizeOutput = (str: string) =>
                (str || '').replace(/\r\n/g, '\n').trim().split('\n').map(line => line.trim()).join('\n');

              const stdout = normalizeOutput(res?.stdout || '');
              const stderr = (res?.stderr || '').trim();
              const compileOutput = (res?.compile_output || '').trim();
              const status = res?.status?.description || 'Unknown';
              const expected = normalizeOutput(test.expected_output || '');
              const passed = stdout === expected;

              const testName = `Test ${index + 1}`;
              let detailMessage = `========================================\n`;
              detailMessage += `${testName}\n`;
              detailMessage += `========================================\n\n`;
              detailMessage += `Input:\n${test.input || '(empty)'}\n\n`;
              detailMessage += `Expected Output:\n${expected}\n\n`;
              detailMessage += `Your Output:\n${stdout || '(no output)'}\n\n`;
              detailMessage += `Status: ${status}\n`;
              detailMessage += `Result: ${passed ? 'PASSED' : 'FAILED'}\n`;

              if (stderr) {
                detailMessage += `\nError Output:\n${stderr}\n`;
              }
              if (compileOutput) {
                detailMessage += `\nCompilation Output:\n${compileOutput}\n`;
              }

              this.detailedOutput.push(detailMessage);
              this.testResults.push({
                name: testName,
                message: passed ? 'Passed' : `Expected: "${expected}", Got: "${stdout}"`,
                passed,
                stdout,
                stderr,
                expected
              });

              if (passed) passedCount++;
              completed++;

              if (completed === tests.length) {
                this.allPassed = passedCount === tests.length;

                if (this.allPassed) {
                  this.stopTimer();
                  this.earnedPoints = this.calculatePoints();
                  this.submitSolution();
                } else {
                  this.consoleOutput = `${passedCount}/${tests.length} tests passed`;
                }

                this.programOutput = this.detailedOutput.join('\n\n');

                this.addToCache(cacheKey, {
                  showResults: this.showResults,
                  testResults: this.testResults,
                  allPassed: this.allPassed,
                  consoleOutput: this.consoleOutput,
                  programOutput: this.programOutput
                });

                this.isRunning = false;
              }
            },
            error: err => {
              completed++;
              let errorMsg = `========================================\n`;
              errorMsg += `Test ${index + 1}\n`;
              errorMsg += `========================================\n\n`;
              errorMsg += `Runtime or Compilation Error\n\n`;
              errorMsg += `Input:\n${test.input || '(empty)'}\n\n`;
              errorMsg += `Error Details:\n${err.error?.stderr || err.message || 'Unknown error occurred'}\n`;

              this.detailedOutput.push(errorMsg);
              this.testResults.push({
                name: `Test ${index + 1}`,
                message: 'Runtime error',
                passed: false
              });

              if (completed === tests.length) {
                this.consoleOutput = 'Compilation or runtime error occurred';
                this.programOutput = this.detailedOutput.join('\n\n');
                this.isRunning = false;
              }
            }
          });
        });
      },

      error: err => {
        console.error('Error loading tests:', err);
        this.consoleOutput = 'Error loading test cases';
        this.showResults = true;
        this.isRunning = false;
      }
    });
  }

  submitSolution(): void {
    const user = this.userService.getCurrentUser();
    if (!user) {
      this.consoleOutput = "User not logged in!";
      return;
    }
    const elapsedSeconds = Math.floor((Date.now() - this.startTime) / 1000);

    const payload = {
      idUser: user.id,
      idExercise: this.exercise.id,
      pointsEarned: this.earnedPoints,
      timeTaken: elapsedSeconds,
      code: this.userCode
    };

    this.http.post(`${environment.apiUrl}/submissions`, payload, { withCredentials: true }).subscribe({
      next: (response: any) => {
        if (response.success) {
          this.consoleOutput = response.message;
          this.alreadyCompleted = true;
          this.testCache.clear();
        } else {
          this.consoleOutput = response.message;
          this.earnedPoints = 0;
        }
      },
      error: (err) => {
        console.error('Error submitting solution:', err);
        if (this.allPassed) {
          const points = this.earnedPoints > 0 ? this.earnedPoints : 0;
          this.consoleOutput = `Congratulations! You completed the exercise!`;
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.stopTimer();
  }

}

