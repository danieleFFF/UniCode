import {Component, ElementRef, HostListener, OnInit} from '@angular/core'
import {CommonModule} from '@angular/common'
import {FormsModule} from '@angular/forms'
import {HttpClient, HttpClientModule, HttpParams} from '@angular/common/http'
import {RouterLink} from '@angular/router'
import {Navbar} from '../../layout/navbar/navbar';
import {environment} from '../../../environments/environment';

interface Exercise {
  id: number
  title: string
  difficulty: 'Easy' | 'Medium' | 'Hard'
  points: number
  description: string
  id_language: number
}

interface TestCaseData {
  input: string;
  expected_output: string;
}

interface NewExerciseRequest {
  exercise: {
    title: string;
    description: string;
    difficulty: 'Easy' | 'Medium' | 'Hard';
    points: number;
    id_language: number;
    solution_demo?: string;
  };
  testCases: TestCaseData[];
}

@Component({
  selector: 'app-exercises',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink, Navbar],
  templateUrl: './exercises.component.html',
  styleUrls: ['./exercises.component.scss']
})
export class ExercisesComponent implements OnInit {
  exercises: Exercise[] = []
  languages = ['C++', 'Python', 'JavaScript', 'HTML', 'SQL']
  selectedLanguage = 'C++'
  sortBy = 'title'
  sortOrder: 'asc' | 'desc' = 'asc'
  showFilterMenu = false
  showLanguageMenu = false
  loading = false
  page = 0
  size = 10
  hasMore = true
  selectedFilterName = 'Alphabetic'
  isUserAdmin: boolean = false;
  showAdminPopup: boolean = false;
  errorMessage: string = '';

  newExerciseData: NewExerciseRequest = {
    exercise: {
      title: '',
      description: '',
      //informazioni di default provvisorie
      difficulty: 'Easy',
      points: 10,
      id_language: 1
    },
    testCases: [{ input: '', expected_output: '' }]
  };

  constructor(private http: HttpClient, private eRef: ElementRef) { }

  ngOnInit() {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const savedLang = localStorage.getItem('selectedLanguage')
      const savedSort = localStorage.getItem('sortBy')
      const savedOrder = localStorage.getItem('sortOrder')
      this.selectedLanguage = savedLang || 'C++'
      this.sortBy = savedSort || 'title'
      this.sortOrder = (savedOrder as 'asc' | 'desc') || 'asc'
      const userJson = localStorage.getItem('user');
      if (userJson) {
        try {
          const user = JSON.parse(userJson);
          if (user.is_admin === true || user.admin === true) {
            this.isUserAdmin = true;
          }
        } catch (e) {
          console.error('Errore parsing JSON locale', e);
        }
      }
      if (!this.isUserAdmin) {
        this.http.get<any>(`${environment.apiUrl}/users/profile`, { withCredentials: true })
          .subscribe({
            next: (userDto) => {
              console.log("Profilo recuperato in ritardo:", userDto);
              localStorage.setItem('user', JSON.stringify(userDto));
              if (userDto.is_admin === true || userDto.admin === true) {
                this.isUserAdmin = true;
              }
            },
            error: () => {
              console.log("Utente non loggato o sessione scaduta.");
              this.isUserAdmin = false;
            }
          });
      }
    }

    this.selectedFilterName = this.mapSortName(this.sortBy)
    this.loadExercises(true)
  }

  toggleAdminPopup() {
    this.showAdminPopup = !this.showAdminPopup;
    if (this.showAdminPopup) {
      this.newExerciseData.exercise.id_language = this.getLanguageId(this.selectedLanguage);
      this.errorMessage = '';
    }
  }

  closeAdminPopup() {
    this.showAdminPopup = false;
    this.errorMessage = '';
  }

  addTestCase() {
    this.newExerciseData.testCases.push({ input: '', expected_output: '' });
  }

  removeTestCase(index: number) {
    if (this.newExerciseData.testCases.length > 1) {
      this.newExerciseData.testCases.splice(index, 1);
    }
  }

  saveExercise() {
    this.errorMessage = '';
    if (!this.newExerciseData.exercise.title || !this.newExerciseData.exercise.description) {
      this.errorMessage = 'Insert all required fields';
      return;
    }

    this.http.post(`${environment.apiUrl}/exercises`, this.newExerciseData, { withCredentials: true, responseType: 'text' })
      .subscribe({
        next: () => {
          this.closeAdminPopup();
          this.loadExercises(true);
          this.newExerciseData = {
            exercise: {
              title: '',
              description: '',
              difficulty: 'Easy',
              points: 10,
              id_language: this.getLanguageId(this.selectedLanguage)
            },
            testCases: [{ input: '', expected_output: '' }]
          };
        },
        error: (err) => {
          console.error('Errore salvataggio:', err);
        }
      });
  }

  loadExercises(reset: boolean = false) {
    if (this.loading || (!this.hasMore && !reset)) return
    this.loading = true

    if (reset) {
      this.exercises = []
      this.page = 0
      this.hasMore = true
    }

    const idLang = this.getLanguageId(this.selectedLanguage)
    const params = new HttpParams()
      .set('idLanguage', idLang.toString())
      .set('sortBy', this.sortBy)
      .set('order', this.sortOrder)

    this.http.get<Exercise[]>(`${environment.apiUrl}/exercises`, { params, withCredentials: true }).subscribe({
      next: (data) => {
        this.exercises = data
        this.loading = false
      },
      error: (err) => {
        console.error('Error loading exercises:', err)
        this.loading = false
      }
    })
  }

  @HostListener('window:scroll', [])
  onScroll() {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 200 && !this.loading) {
      this.loadExercises()
    }
  }

  @HostListener('document:click', ['$event'])
  handleOutsideClick(event: MouseEvent) {
    const target = event.target as HTMLElement
    const languageMenu = this.eRef.nativeElement.querySelector('.language-menu')
    const filterMenu = this.eRef.nativeElement.querySelector('.filter-menu')
    const languageButton = this.eRef.nativeElement.querySelector('.language-button')
    const filterButton = this.eRef.nativeElement.querySelector('.filter-button')

    if (this.showLanguageMenu && !languageMenu?.contains(target) && !languageButton?.contains(target)) {
      this.showLanguageMenu = false
    }

    if (this.showFilterMenu && !filterMenu?.contains(target) && !filterButton?.contains(target)) {
      this.showFilterMenu = false
    }
  }
  toggleLanguageMenu(event: MouseEvent) {
    event.stopPropagation()
    this.showLanguageMenu = !this.showLanguageMenu
    if (this.showLanguageMenu) this.showFilterMenu = false
  }

  toggleFilterMenu(event: MouseEvent) {
    event.stopPropagation()
    this.showFilterMenu = !this.showFilterMenu
    if (this.showFilterMenu) this.showLanguageMenu = false
  }

  selectLanguage(lang: string) {
    this.selectedLanguage = lang
    if (typeof localStorage !== 'undefined') localStorage.setItem('selectedLanguage', lang)
    this.showLanguageMenu = false
    this.loadExercises(true)
  }

  applySort(type: string) {
    this.sortBy = type
    this.selectedFilterName = this.mapSortName(type)
    if (typeof localStorage !== 'undefined') localStorage.setItem('sortBy', type)
    this.showFilterMenu = false
    this.loadExercises(true)
  }

  toggleOrder() {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc'
    if (typeof localStorage !== 'undefined') localStorage.setItem('sortOrder', this.sortOrder)
    this.loadExercises(true)
  }

  getXpColor(difficulty: string): string {
    switch (difficulty) {
      case 'Easy': return '#6eff7f'
      case 'Medium': return '#ffe769'
      case 'Hard': return '#ff6f6f'
      default: return '#fff'
    }
  }

  getLanguageId(name: string): number {
    switch (name) {
      case 'Python': return 1
      case 'C++': return 2
      case 'SQL': return 3
      case 'JavaScript': return 4
      case 'HTML': return 5
      default: return 0
    }
  }
  mapSortName(type: string): string {
    switch (type) {
      case 'difficulty': return 'Difficulty'
      case 'points': return 'Points'
      default: return 'Alphabetic'
    }
  }
}
