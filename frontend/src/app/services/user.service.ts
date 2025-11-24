import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User{
  id: number;
  username: string;
  email: string;
  total_points: number;
  id_avatar: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  updateAvatar(avatarId: number): Observable<any> {
    // Prendiamo il Token
    const token = localStorage.getItem('authToken');

    // Prepariamo l'intestazione con il pass
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    //Prepariamo il pacchetto JSON
    const body = { avatarId: avatarId };

    //  Spediamo con una PUT
    return this.http.put(`${this.apiUrl}/update-avatar`, body, { headers });
  }

  getProfile(){

    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.apiUrl+"/profile"}`, { headers });
  }

}
