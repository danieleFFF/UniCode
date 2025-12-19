import { Component, OnInit, OnDestroy } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { ActivatedRoute } from '@angular/router'
import { HttpClient, HttpClientModule } from '@angular/common/http'
import { Navbar } from '../../layout/navbar/navbar'
import { UserService } from '../../services/user.service';

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
export class SolveComponent implements OnInit, OnDestroy{
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
  isRunning = false
  alreadyCompleted = false
  private testCache: Map<string, CachedResult> = new Map();
  private readonly MAX_CACHE_SIZE = 50;
  constructor(private route: ActivatedRoute, private http: HttpClient, private userService: UserService) { }

  ngOnInit(): void {
    this.resetState();
    const id = this.route.snapshot.paramMap.get('id');

    this.http.get(`/api/exercises/${id}`).subscribe((data: any) =>{
      this.exercise = data;
      this.selectedLanguage = data.languageName || 'Python';
      this.currentPoints = this.exercise.points || 100;
      this.timeLeft = 600;
      this.startTime = Date.now();
      this.startTimer();
      this.checkIfAlreadyCompleted();
    });
  }

  checkIfAlreadyCompleted(): void {
    const user = this.userService.getCurrentUser();
    if (!user) return;

    this.http.get(`/api/submissions/check/${user.id}/${this.exercise.id}`).subscribe({
      next: (response:any) => {
        this.alreadyCompleted = response.completed;
        if(this.alreadyCompleted){
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
      if (this.timeLeft > 0){
        this.timeLeft--
        this.updateFormattedTime()
      } else{
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

  private addToCache(key: string, value: CachedResult):void{
    if (this.testCache.size >= this.MAX_CACHE_SIZE) {
      const firstKey =this.testCache.keys().next().value;
      if (typeof firstKey === "string")  {
        this.testCache.delete(firstKey);
      }
    }
    this.testCache.set(key, value);
  }

  runTests(): void {
    if(!this.exercise || !this.exercise.id) {
      console.warn('No exercise loaded');
      return;
    }

    if(!this.userCode || this.userCode.trim() === '') {
      this.consoleOutput = 'Please write some code first';
      this.showResults = true;
      return;
    }

    if (this.isRunning) {
      return;
    }

    if (this.alreadyCompleted){
      this.consoleOutput = 'You have already completed this exercise! No additional points awarded.';
      this.showResults = true;
      return;
    }

    const cacheKey = `${this.exercise.id}_${this.userCode}`;

    if(this.testCache.has(cacheKey)) {
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

    const languageMap: { [key: string]: number } ={
      'python': 71,
      'c++': 54,
      'java': 62,
      'javascript': 63
    };

    const lang = (this.selectedLanguage || 'python').toLowerCase();
    const langId= languageMap[lang];

    if (!langId) {
      this.consoleOutput = `Language ${this.selectedLanguage} not supported`;
      this.showResults = true;
      this.isRunning = false;
      return;
    }

    this.http.get(`/api/exercises/${this.exercise.id}/tests`).subscribe({
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

          this.http.post(`/api/exercises/${this.exercise.id}/run`, payload).subscribe({
            next: (res: any) => {
              const stdout = (res?.stdout || '').trim();
              const stderr = (res?.stderr || '').trim();
              const compileOutput = (res?.compile_output || '').trim();
              const status= res?.status?.description || 'Unknown';
              const expected = (test.expected_output || '').trim();
              const passed = stdout === expected;

              const testName = `Test ${index + 1}`;
              let detailMessage = `========================================\n`;
              detailMessage += `${testName}\n`;
              detailMessage += `========================================\n\n`;
              detailMessage += `Input:\n${test.input || '(empty)'}\n\n`;
              detailMessage += `Expected Output:\n${expected}\n\n`;
              detailMessage += `Your Output:\n${stdout || '(no output)'}\n\n`;
              detailMessage += `Status: ${status}\n`;
              detailMessage += `Result: ${passed ? 'PASSED' :'FAILED'}\n`;

              if (stderr){
                detailMessage += `\nError Output:\n${stderr}\n`;
              }
              if(compileOutput){
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

              if(passed) passedCount++;
              completed++;

              if (completed === tests.length) {
                this.allPassed = passedCount === tests.length;

                if (this.allPassed){
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

    this.http.post('/api/submissions', payload).subscribe({
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
        this.consoleOutput = `All tests passed! You earned ${this.earnedPoints} points!`;
      }
    });
  }

  ngOnDestroy(): void  {
    this.stopTimer();
  }

}

