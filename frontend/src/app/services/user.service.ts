import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';



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

  private url = 'http://localhost:8080/api/users';

  constructor(private http : HttpClient) {}

  getProfile(): Observable<User>{
    return this.http.get<User>(this.url + '/profile');
  }

  changePassword(newPassword:String) : Observable<string>{
    return this.http.put(this.url + '/password', newPassword, {responseType: 'text'});
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

}
