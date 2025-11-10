import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http';

interface Exercise {
  id: number;
  title: string;
  difficulty: 'Easy' | 'Medium' | 'Hard';
  points: number;
  description: string;
  id_language: number;
}

@Component({
  selector: 'app-exercises',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './exercises.component.html',
  styleUrls: ['./exercises.component.scss']
})
export class ExercisesComponent implements OnInit {
  exercises: Exercise[] = [];
  languages = ['C++', 'Python', 'Java', 'JavaScript', 'HTML', 'SQL'];
  selectedLanguage = 'C++';
  sortBy = 'title';
  sortOrder: 'asc' | 'desc' = 'asc';
  showFilterMenu = false;
  loading = false;
  page = 0;
  size = 10;
  hasMore = true;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const savedLang = localStorage.getItem('selectedLanguage');
    const savedSort = localStorage.getItem('sortBy');
    const savedOrder = localStorage.getItem('sortOrder');

    if (savedLang) this.selectedLanguage = savedLang;
    if (savedSort) this.sortBy = savedSort;
    if (savedOrder) this.sortOrder = savedOrder as 'asc' | 'desc';

    this.loadExercises(true);
  }

  loadExercises(reset: boolean = false) {
    if (this.loading || (!this.hasMore && !reset)) return;
    this.loading = true;

    if (reset) {
      this.exercises = [];
      this.page = 0;
      this.hasMore = true;
    }

    const idLang = this.getLanguageId(this.selectedLanguage);
    const params = new HttpParams()
      .set('idLanguage', idLang)
      .set('sortBy', this.sortBy)
      .set('order', this.sortOrder)
      .set('page', this.page)
      .set('size', this.size);

    this.http.get<Exercise[]>('/api/exercises', { params }).subscribe({
      next: (data) => {
        if (data.length < this.size) this.hasMore = false;
        this.exercises = [...this.exercises, ...data];
        this.page++;
        this.loading = false;
      },
      error: (err) => {
        console.error('Errore caricamento esercizi:', err);
        this.loading = false;
      }
    });
  }

  @HostListener('window:scroll', [])
  onScroll() {
    if (
      window.innerHeight + window.scrollY >= document.body.offsetHeight - 200 &&
      !this.loading
    ) {
      this.loadExercises();
    }
  }

  onLanguageChange() {
    localStorage.setItem('selectedLanguage', this.selectedLanguage);
    this.loadExercises(true);
  }

  applySort(type: string) {
    this.sortBy = type;
    localStorage.setItem('sortBy', type);
    this.showFilterMenu = false;
    this.loadExercises(true);
  }

  toggleFilterMenu(): void {
    this.showFilterMenu = !this.showFilterMenu;
  }

  toggleOrder() {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    localStorage.setItem('sortOrder', this.sortOrder);
    this.loadExercises(true);
  }

  getXpColor(difficulty: string): string {
    switch (difficulty) {
      case 'Easy': return '#6eff7f';
      case 'Medium': return '#ffe769';
      case 'Hard': return '#ff6f6f';
      default: return '#fff';
    }
  }

  getLanguageId(name: string): number {
    switch (name) {
      case 'Python': return 1;
      case 'C++': return 2;
      case 'Java': return 3;
      case 'JavaScript': return 4;
      case 'HTML': return 5;
      case 'SQL': return 6;
      default: return 0;
    }
  }
}
