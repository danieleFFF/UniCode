import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { RegisterPayload } from '../models/register.model';

@Injectable({
  providedIn:'root'
})
export class AuthService{
  private apiUrl='http://localhost:8080/api';
  private isLoggedInSubject=new BehaviorSubject<boolean>(false);
  public isLoggedIn$=this.isLoggedInSubject.asObservable();
  private readonly isBrowser:boolean;
  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId:Object
  ){
    this.isBrowser=isPlatformBrowser(this.platformId);
    if(this.isBrowser){
      this.checkInitialLoginStatus();
    }
  }
  private checkInitialLoginStatus():void{
    if(this.isBrowser){
      const token=!!localStorage.getItem('authToken');
      this.isLoggedInSubject.next(token);
    }
  }
  register(userData:RegisterPayload):Observable<any>{
    return this.http.post(`${this.apiUrl}/users/register`,userData,{responseType:'text'});
  }

  sendPasswordRecoverEmail(email: string): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/auth/send-reset-code`, email);
  }

  login(credentials:{email:string,password:string}):Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/auth/login`,credentials).pipe(
      tap((response)=>{
        if(this.isBrowser){
          localStorage.setItem('authToken', response.token);
        }
        this.isLoggedInSubject.next(true);
      }),
      catchError(error=>{
        if(this.isBrowser){
          localStorage.removeItem('authToken');
        }
        this.isLoggedInSubject.next(false);
        console.error('Login fallito nel servizio',error);
        return throwError(()=>error);
      })
    );
  }
  logout():void{
    if(this.isBrowser){
      localStorage.removeItem('authToken');
    }
    this.isLoggedInSubject.next(false);
    this.router.navigate(['/home']).then(()=>{});
  }

  //metodo che ancora non viene utilizzato ma che servirà per le funzioni accessibili solo agli utenti loggati
  isLoggedIn(): boolean {
    return this.isLoggedInSubject.getValue();
  }
}
