import { Component, HostListener, OnInit, ElementRef } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http'
import { RouterLink } from '@angular/router'

interface Exercise {
  id: number
  title: string
  difficulty: 'Easy' | 'Medium' | 'Hard'
  points: number
  description: string
  id_language: number
}

@Component({
  selector: 'app-exercises',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './exercises.component.html',
  styleUrls: ['./exercises.component.scss']
})
export class ExercisesComponent implements OnInit {
  exercises: Exercise[] = []
  languages = ['C++', 'Python', 'Java', 'JavaScript', 'HTML', 'SQL']
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

  constructor(private http: HttpClient, private eRef: ElementRef) {}

  ngOnInit() {
    const savedLang = localStorage.getItem('selectedLanguage')
    const savedSort = localStorage.getItem('sortBy')
    const savedOrder = localStorage.getItem('sortOrder')

    this.selectedLanguage = savedLang || 'C++'
    this.sortBy = savedSort || 'title'
    this.sortOrder = (savedOrder as 'asc' | 'desc') || 'asc'
    this.selectedFilterName = this.mapSortName(this.sortBy)
    this.loadExercises(true)
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
      .set('language', idLang.toString())
      .set('sortBy', this.sortBy)
      .set('order', this.sortOrder)
      .set('page', this.page)
      .set('size', this.size)

    this.http.get<Exercise[]>('/api/exercises', { params }).subscribe({
      next: (data) => {
        if (data.length < this.size) this.hasMore = false
        this.exercises = [...this.exercises, ...data]
        this.page++
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
    if (
      window.innerHeight + window.scrollY >= document.body.offsetHeight - 200 &&
      !this.loading
    ) {
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

    if (
      this.showLanguageMenu &&
      !languageMenu?.contains(target) &&
      !languageButton?.contains(target)
    ) {
      this.showLanguageMenu = false
    }

    if (
      this.showFilterMenu &&
      !filterMenu?.contains(target) &&
      !filterButton?.contains(target)
    ) {
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
    localStorage.setItem('selectedLanguage', lang)
    this.showLanguageMenu = false
    this.loadExercises(true)
  }

  applySort(type: string) {
    this.sortBy = type
    this.selectedFilterName = this.mapSortName(type)
    localStorage.setItem('sortBy', type)
    this.showFilterMenu = false
    this.loadExercises(true)
  }

  toggleOrder() {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc'
    localStorage.setItem('sortOrder', this.sortOrder)
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
      case 'Java': return 3
      case 'JavaScript': return 4
      case 'HTML': return 5
      case 'SQL': return 6
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
