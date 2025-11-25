import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users/profile';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<User> {
    // 1. Recuperiamo il token salvato al momento del login
    // (Assicurati di averlo chiamato 'authToken' nel login, altrimenti cambia il nome qui)
    const token = localStorage.getItem('authToken');

    // 2. Creiamo l'header "Authorization: Bearer <token>"
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    // 3. Facciamo la chiamata passando gli headers
    return this.http.get<User>(this.apiUrl, { headers: headers });
  }
}
