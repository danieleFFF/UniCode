import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Avatar {
  id: number;
  url_immagine: string;
}

@Injectable({
  providedIn: 'root'
})
export class AvatarService {

  private apiUrl = environment.apiUrl + '/avatars';

  constructor(private http: HttpClient) { }

  getAvatars(): Observable<Avatar[]> {
    return this.http.get<Avatar[]>(this.apiUrl , {withCredentials: true});
  }
}
