import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.apiUrl + '/users';
  private currentUser: User | null = null;

  constructor(private http : HttpClient) {}

  getProfile(): Observable<User>{
    return this.http.get<User>(this.url + '/profile', { withCredentials: true }).pipe(
      tap(user => this.currentUser = user)
    );
  }
  clearUser(): void {
    this.currentUser = null;
  }

  changePassword(currentPassword: string, newPassword: string): Observable<string> {
    return this.http.put(this.url + '/password',
      { currentPassword, newPassword },
      { responseType: 'text', withCredentials: true }
    );
  }

  changeAvatar(avatarId: number): Observable<string> {
    return this.http.put(this.url+'/avatar', null, {
      params: { avatarId: avatarId.toString() },
      responseType: 'text',
      withCredentials: true
    }).pipe(
      tap(() => {
        // Ricarica profilo dopo cambio avatar
        this.getProfile().subscribe();
      })
    );
  }

  deleteUser(password : string): Observable<string>{
    return this.http.delete(this.url + '/delete', {
      responseType: 'text',
      body: {password: password},
      withCredentials: true
    });
  }

  getRanking(numero: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.url}/leaderboard?limit=${numero}`, {
      withCredentials: true
    });
  }
}
