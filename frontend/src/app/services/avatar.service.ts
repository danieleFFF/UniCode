import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Avatar {
  id: number;
  url_immagine: string;
}

@Injectable({
  providedIn: 'root'
})
export class AvatarService {

  private apiUrl = 'http://localhost:8080/api/avatars';

  constructor(private http: HttpClient) { }

  getAvatars(): Observable<Avatar[]> {
    return this.http.get<Avatar[]>(this.apiUrl);
  }
}
