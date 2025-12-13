import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, BehaviorSubject, tap, catchError, throwError} from 'rxjs';
import { Router } from '@angular/router';
import { RegisterPayload } from '../models/register.model';
import {CredentialsModel} from '../models/credentials.model';

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
      this.checkAuthStatus();
    }
  }

  private checkAuthStatus(): void {
    this.http.get(`${this.apiUrl}/users/profile`, { withCredentials: true })
      .subscribe({
        next: () => this.isLoggedInSubject.next(true),
        error: () => this.isLoggedInSubject.next(false)
      });
  }

  register(userData:RegisterPayload):Observable<any>{
    return this.http.post(`${this.apiUrl}/users/register`,userData,{responseType:'text' , withCredentials:true});
  }

  sendPasswordRecoverEmail(email: string): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/auth/send-reset-code`, email , {withCredentials:true});
  }

  //TODO : Maybe use a proper model, or json
  resetPassword(data:CredentialsModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/reset-password`, data , {withCredentials:true});
  }

  login(credentials:{email:string,password:string}):Observable<any>{

    const body = new URLSearchParams();
    body.set('username', credentials.email);
    body.set('password', credentials.password);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })

    return this.http.post(`${this.apiUrl}/login`, body.toString() , {
      headers,
      withCredentials:true,
      responseType:'text'
    }).pipe(
      tap(() => {
        this.isLoggedInSubject.next(true);
      }),
      catchError(error => {
        this.isLoggedInSubject.next(false);
        console.error('Login fallito', error);
        return throwError(() => error);
      })
    );
  }


  logout():void{
    this.http.post(`${this.apiUrl}/logout`, {}, {
      withCredentials: true,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.isLoggedInSubject.next(false);
        this.router.navigate(['/home']);
      },
      error: () => {
        // Anche se fallisce, fai logout locale
        this.isLoggedInSubject.next(false);
        this.router.navigate(['/home']);
      }
    });
  }

  //metodo che ancora non viene utilizzato ma che servirà per le funzioni accessibili solo agli utenti loggati
  isLoggedIn(): boolean {
    return this.isLoggedInSubject.getValue();
  }
}
