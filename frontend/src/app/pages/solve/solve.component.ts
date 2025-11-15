import { Component, OnInit, OnDestroy } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { ActivatedRoute } from '@angular/router'
import { HttpClient, HttpClientModule } from '@angular/common/http'

@Component({
  selector: 'app-solve',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './solve.component.html',
  styleUrls: ['./solve.component.scss']
})
export class SolveComponent implements OnInit, OnDestroy {
  exercise: any
  userCode = ''
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

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    const lang = this.route.snapshot.queryParamMap.get('language');
    this.selectedLanguage = lang || '';

    this.http.get(`/api/exercises/${id}`).subscribe((data: any) => {
      this.exercise = data;
      this.selectedLanguage = this.selectedLanguage || this.exercise.language?.name || 'C++';
      this.currentPoints = this.exercise.points || 100;
      this.timeLeft = 600;
      this.startTime = Date.now();
      this.startTimer();
    });
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

  runTests(): void {
    if (!this.exercise || !this.exercise.id) {
      console.warn('No exercise loaded');
      return;
    }

    if (!this.userCode || this.userCode.trim() === '') {
      this.consoleOutput = '⚠️ Please write some code first!';
      this.showResults = true;
      return;
    }

    this.showResults = false;
    this.testResults = [];
    this.consoleOutput = '';
    this.programOutput = '';
    this.detailedOutput = [];
    this.allPassed = false;
    this.earnedPoints = 0;

    const languageMap: any = {
      python: 71,
      'c++': 54,
      java: 62,
      javascript: 63,
      sql: 82
    };

    const lang = (this.selectedLanguage || 'python').toLowerCase();
    const langId = languageMap[lang] || 71;

    this.http.get(`/api/exercises/${this.exercise.id}/tests`).subscribe({
      next: (tests: any) => {
        if (!tests || tests.length === 0) {
          this.consoleOutput = 'No test cases found';
          this.showResults = true;
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

          this.http.post(`/api/exercises/${this.exercise.id}/run`, payload).subscribe({
            next: (res: any) => {
              const stdout = (res?.stdout || '').trim();
              const stderr = (res?.stderr || '').trim();
              const compileOutput = (res?.compile_output || '').trim();
              const status = res?.status?.description || 'Unknown';
              const expected = (test.expected_output || '').trim();
              const passed = stdout === expected;

              const testName = `Test ${index + 1}`;
              let detailMessage = `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n`;
              detailMessage += `📝 ${testName}\n`;
              detailMessage += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n`;

              detailMessage += `📥 Input:\n${test.input || '(empty)'}\n\n`;
              detailMessage += `✅ Expected Output:\n${expected}\n\n`;
              detailMessage += `📤 Your Output:\n${stdout || '(no output)'}\n\n`;
              detailMessage += `🔍 Status: ${status}\n`;
              detailMessage += `⚡ Result: ${passed ? '✓ PASSED' : '✗ FAILED'}\n`;

              if (stderr) {
                detailMessage += `\n⚠️ Error Output:\n${stderr}\n`;
              }
              if (compileOutput) {
                detailMessage += `\n🔧 Compilation Output:\n${compileOutput}\n`;
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
                  this.consoleOutput = `✅ All test cases passed! You earned ${this.earnedPoints} points!`;
                } else {
                  this.consoleOutput = `⚠️ ${passedCount}/${tests.length} tests passed`;
                }

                this.programOutput = this.detailedOutput.join('\n\n');
              }
            },
            error: err => {
              completed++;
              let errorMsg = `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n`;
              errorMsg += `📝 Test ${index + 1}\n`;
              errorMsg += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n`;
              errorMsg += `❌ Runtime or Compilation Error\n\n`;
              errorMsg += `📥 Input:\n${test.input || '(empty)'}\n\n`;
              errorMsg += `⚠️ Error Details:\n${err.error?.stderr || err.message || 'Unknown error occurred'}\n`;

              this.detailedOutput.push(errorMsg);

              this.testResults.push({
                name: `Test ${index + 1}`,
                message: 'Runtime error',
                passed: false
              });

              if (completed === tests.length) {
                this.consoleOutput = '❌ Compilation or runtime error occurred';
                this.programOutput = this.detailedOutput.join('\n\n');
              }
            }
          });
        });
      },
      error: err => {
        console.error('Error loading tests:', err);
        this.consoleOutput = '⚠️ Error loading test cases';
        this.showResults = true;
      }
    });
  }

  ngOnDestroy(): void {
    this.stopTimer();
  }
}
