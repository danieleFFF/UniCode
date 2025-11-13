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

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    const lang = this.route.snapshot.queryParamMap.get('language');
    this.selectedLanguage = lang || '';

    this.http.get(`/api/exercises/${id}`).subscribe((data: any) => {
      this.exercise = data;
      this.selectedLanguage = this.selectedLanguage || this.exercise.language?.name || 'C++';
      this.currentPoints = this.exercise.points || 100;
      this.timeLeft = this.exercise.timer || 600;
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
        this.currentPoints = Math.floor(this.currentPoints * 0.5)
      }
    }, 1000)
  }

  updateFormattedTime(): void {
    const minutes = Math.floor(this.timeLeft / 60)
    const seconds = this.timeLeft % 60
    this.formattedTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`
  }

  runTests(): void {
    console.log('🧠 runTests() triggered');
    if (!this.exercise || !this.exercise.id) {
      console.warn('⚠️ Nessun esercizio caricato — impossibile eseguire i test');
      return;
    }

    this.showResults = false;
    this.testResults = [];
    this.consoleOutput = '';
    this.allPassed = false;

    const languageMap: any = {
      python: 71,
      cpp: 54,
      java: 91,
      javascript: 63,
      sql: 82
    };

    const lang = (this.selectedLanguage || this.exercise?.language?.name || 'python').toLowerCase();
    const langId = languageMap[lang] || 71;

    console.log('▶️ Eseguo test per', lang, '(ID', langId, ') esercizio', this.exercise.id);

    this.http.get(`/api/exercises/${this.exercise.id}/tests`).subscribe({
      next: (tests: any) => {
        console.log('✅ Test caricati:', tests);
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
              const output = (res?.stdout || '').trim();
              const expected = (test.output_atteso || '').trim();
              const passed = output === expected;

              this.testResults.push({
                name: test.name || `Test ${index + 1}`,
                message: passed ? 'OK' : `Expected "${expected}", got "${output}"`,
                passed
              });

              if (passed) passedCount++;
              completed++;

              if (completed === tests.length) {
                this.allPassed = passedCount === tests.length;
                this.consoleOutput = this.allPassed
                  ? '✅ All test cases succeeded'
                  : '❌ Some test cases failed';
              }
            },
            error: err => {
              completed++;
              console.error('Errore run:', err);
              this.consoleOutput = '❌ Compilation error';
            }
          });
        });
      },
      error: err => {
        console.error('❌ Errore caricamento test:', err);
        this.consoleOutput = '⚠️ Error loading test cases.';
        this.showResults = true;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.timer) clearInterval(this.timer)
  }
}
