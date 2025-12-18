import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Trophy } from '../models/trophy.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TrophyService {
  private apiUrl = environment.apiUrl + '/trophies';

  constructor(private http: HttpClient) { }

  getUserTrophies(userId: number): Observable<Trophy[]> {
    return this.http.get<Trophy[]>(`${this.apiUrl}/user/${userId}`, {
      withCredentials: true
    });
  }

  checkTrophies(userId: number): Observable<Trophy[]> {
    return this.http.post<Trophy[]>(`${this.apiUrl}/check/${userId}`, {}, {
      withCredentials: true
    });
  }

  getTrophyImagePath(trophyCode: string): string {
    const imageMap: { [key: string]: string } = {
      'EXERCISE_20': 'assets/images/trophies/thropies20Excersize.png',
      'EXERCISE_50': 'assets/images/trophies/thrtopies50excersise.png',
      'EXERCISE_100': 'assets/images/trophies/thropies100excersise.png',
      'CODE_MASTER': 'assets/images/trophies/trhopiesCodeMaster.png',
      'PYTHON_MASTER': 'assets/images/trophies/thropiesPythonMaster.png',
      'TIME_MASTER': 'assets/images/trophies/thropiesTime.png',
      'FIRST_RANK': 'assets/images/trophies/thropies!rank.png'
    };
    return imageMap[trophyCode] || '';
  }
}
