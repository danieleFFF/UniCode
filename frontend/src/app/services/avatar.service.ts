// avatar.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definiamo come è fatto un Avatar (uguale al tuo JSON!)
export interface Avatar {
  id: number;
  url_immagine: string; // IMPORTANTE: deve chiamarsi ESATTAMENTE come nel JSON che vedi nel browser
}

@Injectable({
  providedIn: 'root'
})
export class AvatarService {

  // L'URL del tuo backend
  private apiUrl = 'http://localhost:8080/api/avatars';

  constructor(private http: HttpClient) { }

  getAvatars(): Observable<Avatar[]> {
    return this.http.get<Avatar[]>(this.apiUrl);
  }
}
