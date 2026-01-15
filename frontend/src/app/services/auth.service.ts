import { Injectable } from '@angular/core';
import {HttpClient,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { RegisterPayload } from '../models/register.model';
import {CredentialsModel} from '../models/credentials.model';
import {environment} from '../../environments/environment';
import { UserService } from './user.service';

@Injectable({
  providedIn:'root'
})
export class AuthService{
  private apiUrl=environment.apiUrl;
  constructor(
    private http:HttpClient,
    private router:Router,
    private userService:UserService
  ){}

  register(userData:RegisterPayload):Observable<any>{
    return this.http.post(`${this.apiUrl}/users/register`, userData, {
      responseType: 'text',
      withCredentials: true
    });
  }

  sendPasswordRecoverEmail(email: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/auth/send-reset-code`, email, {
      withCredentials: true
    });
  }

  resetPassword(data:CredentialsModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/reset-password`, data, {
      withCredentials: true
    });
  }

  login(credentials:{email:string; password:string}):Observable<any>{
    const body=new URLSearchParams();
    body.set('username',credentials.email);
    body.set('password',credentials.password);
    const headers=new HttpHeaders({'Content-Type':'application/x-www-form-urlencoded'});
    return this.http.post(`${this.apiUrl}/login`,body.toString(),{headers,withCredentials:true,responseType:'text'});
  }

  logout(redirect:boolean=true):void{
    this.http.post(`${this.apiUrl}/logout`,{},{withCredentials:true,responseType:'text'}).subscribe({
      next:()=>{this.userService.clearUser();
        if(redirect){
          this.router.navigate(['/home']);
        }
      },
      error:()=>{
        this.userService.clearUser();
        if(redirect){
          this.router.navigate(['/home']);
        }
      }
    });
  }
}
