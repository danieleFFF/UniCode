import {Component, OnInit} from '@angular/core';
import { Navbar } from '../../layout/navbar/navbar';
import { Hero } from '../../components/hero/hero';
import { Courses} from '../../components/courses/courses';
import {InfoCards} from '../../components/info-cards/info-cards';
import {NgIf, NgFor} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    Navbar,
    Hero,
    Courses,
    InfoCards,
    NgIf,
    NgFor,
    FormsModule,
    HttpClientModule
  ],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  isUserAdmin: boolean = false;
  showAdminPopup: boolean = false;
  studentUsernames: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userJson = localStorage.getItem('user');
      if (userJson) {
        try {
          const user = JSON.parse(userJson);
          if (user.is_admin === true || user.admin === true) {
            this.isUserAdmin = true;
          }
        } catch (e) {
          console.error('Errore parsing JSON locale', e);
        }
      }

      this.http.get<any>(`${environment.apiUrl}/users/profile`, { withCredentials: true })
        .subscribe({
          next: (userDto) => {
            localStorage.setItem('user', JSON.stringify(userDto));
            this.isUserAdmin = userDto.is_admin === true || userDto.admin === true;
          },
          error: () => {
            this.isUserAdmin = false;
          }
        });
    }
  }

  toggleAdminPopup() {
    this.showAdminPopup = !this.showAdminPopup;
    if (this.showAdminPopup) {
      this.loadStudentUsernames();
    }
  }

  closeAdminPopup() {
    this.showAdminPopup = false;
  }

  loadStudentUsernames() {
    this.http.get<string[]>(`${environment.apiUrl}/users/nonadmin-usernames`, { withCredentials: true })
      .subscribe({
        next: (usernames) => {
          this.studentUsernames= usernames;
        },
        error: (err) => {
          console.error('Error fetching emails', err);
        }
      });
  }
}
