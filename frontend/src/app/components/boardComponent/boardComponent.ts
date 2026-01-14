import {Component, Input, numberAttribute, OnChanges, SimpleChanges} from '@angular/core';
import { TrophyService } from '../../services/trophy.service';
import { Trophy } from '../../models/trophy.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './boardComponent.html',
  styleUrls: ['./boardComponent.scss']
})
export class BoardComponent implements OnChanges {
  @Input({transform: numberAttribute}) userId: number | null = null;

  topRowTrophies: Trophy[] = [];
  bottomRowTrophies: Trophy[] = [];
  loading = true;

  constructor(private trophyService: TrophyService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['userId'] && this.userId) {
      this.loadUserTrophies(this.userId);
    }
  }

  loadUserTrophies(userId: number): void {
    this.loading = true;
    this.trophyService.getUserTrophies(userId).subscribe({
      next: (trophies) => {
        // Aggiungi i path alle immagini
        trophies.forEach(trophy => {
          trophy.path = this.trophyService.getTrophyImagePath(trophy.cod_trophy);
        });
        this.mapTrophiesToRows(trophies);
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  private mapTrophiesToRows(trophies: Trophy[]): void {
    const topCodes = ['EXERCISE_100', 'CODE_MASTER', 'FIRST_RANK'];
    const bottomCodes = ['EXERCISE_50', 'EXERCISE_20', 'PYTHON_MASTER', 'TIME_MASTER'];

    this.topRowTrophies = topCodes.map(code =>
      trophies.find(t => t.cod_trophy === code)!
    ).filter(t => t !== undefined);

    this.bottomRowTrophies = bottomCodes.map(code =>
      trophies.find(t => t.cod_trophy === code)!
    ).filter(t => t !== undefined);
  }
}
