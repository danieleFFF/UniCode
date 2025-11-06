import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http';

interface Exercise {
  id: number;
  title: string;
  difficulty: 'Easy' | 'Medium' | 'Hard';
  language: string;
  points: number;
  description: string;
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
  sortBy = 'title'; // default: alfabetico
  page = 0;
  loading = false;
  hasMore = true;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadExercises(true);
  }

  /** carica esercizi dal backend */
  loadExercises(reset: boolean = false) {
    if (this.loading || (!this.hasMore && !reset)) return;
    this.loading = true;

    if (reset) {
      this.exercises = [];
      this.page = 0;
      this.hasMore = true;
    }

    const params = new HttpParams()
      .set('language', this.selectedLanguage)
      .set('sortBy', this.sortBy)
      .set('page', this.page);

    this.http.get<Exercise[]>('/api/exercises', { params }).subscribe({
      next: (data) => {
        if (data.length === 0) this.hasMore = false;
        else {
          this.exercises = [...this.exercises, ...data];
          this.page++;
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Errore caricamento esercizi:', err);
        this.loading = false;
      }
    });
  }

  /** cambia linguaggio */
  onLanguageChange() {
    this.loadExercises(true);
  }

  /** cambia tipo di filtro */
  changeSort(sortType: string) {
    this.sortBy = sortType;
    this.loadExercises(true);
  }

  /** caricamento automatico scroll */
  @HostListener('window:scroll', [])
  onScroll() {
    if (
      window.innerHeight + window.scrollY >= document.body.offsetHeight - 100 &&
      !this.loading
    ) {
      this.loadExercises();
    }
  }

  /** calcolo XP visualizzato */
  getXpColor(difficulty: string): string {
    switch (difficulty) {
      case 'Easy': return '#6eff7f';
      case 'Medium': return '#ffe769';
      case 'Hard': return '#ff6f6f';
      default: return '#fff';
    }
  }
}
