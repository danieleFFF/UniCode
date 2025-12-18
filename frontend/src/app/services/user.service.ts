import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.apiUrl + '/users';

  constructor(private http : HttpClient) {}

  getProfile(): Observable<User>{
    return this.http.get<User>(this.url + '/profile');
  }

  changePassword(currentPassword: string, newPassword: string): Observable<string> {
    return this.http.put(this.url + '/password',
      { currentPassword, newPassword },
      { responseType: 'text' }
    );
  }

  changeAvatar(avatarId: number): Observable<string> {
    return this.http.put(this.url+'/avatar', null, {
      params: { avatarId: avatarId.toString() },
      responseType: 'text'
    });
  }

  deleteUser(password : string): Observable<string>{
    return this.http.delete(this.url + '/delete', {
      responseType: 'text',
      body: {password: password}
    });
  }

  getRanking(numero: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.url}/leaderboard?limit=${numero}`);
  }

}
