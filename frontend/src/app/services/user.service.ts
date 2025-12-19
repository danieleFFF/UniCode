import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, BehaviorSubject, catchError} from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.apiUrl + '/users';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  public authChecked = false;

  constructor(private http : HttpClient) {}

  getProfile(): Observable<User>{
    return this.http.get<User>(this.url + '/profile', { withCredentials: true }).pipe(
      tap(user => {
        this.currentUserSubject.next(user);
        this.authChecked = true;
      }),
      catchError(err => {
        if (err.status === 401) this.clearUser();
        this.authChecked = true;
        throw err;
      })
    );
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  clearUser(): void {
    this.currentUserSubject.next(null);
    this.authChecked = true;
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
    });
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
