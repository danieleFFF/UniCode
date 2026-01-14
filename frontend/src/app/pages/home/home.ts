import {Component, OnInit} from '@angular/core';
import { Navbar } from '../../layout/navbar/navbar';
import { Hero } from '../../components/hero/hero';
import { Courses} from '../../components/courses/courses';
import {InfoCards} from '../../components/info-cards/info-cards';
import {NgIf, NgFor} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {environment} from '../../../environments/environment';


interface SimpleUser {
  id: number;
  username: string;
}

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
  students: SimpleUser[] = [];
  searchTerm: string = '';
  showPromotePopup: boolean = false;
  userToPromoteId: number | null = null;
  userToPromoteName: string = '';
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
          console.error(e);
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

  get filteredStudents(): SimpleUser[] {
    if (!this.searchTerm) {
      return this.students;
    }
    return this.students.filter(student =>
      student.username.toLowerCase().startsWith(this.searchTerm.toLowerCase())
    );
  }

  toggleAdminPopup() {
    this.showAdminPopup = !this.showAdminPopup;
    if (this.showAdminPopup) {
      this.searchTerm = '';
      this.loadStudents();
    }
  }

  closeAdminPopup() {
    this.showAdminPopup = false;
  }

  loadStudents() {
    this.http.get<SimpleUser[]>(`${environment.apiUrl}/users/nonadmin-users`, {withCredentials: true})
      .subscribe({
        next: (users) => {
          this.students = users;
        },
        error: (err) => console.error(err)
      });
  }

  askMakeAdmin(userId: number, username: string) {
    this.userToPromoteId = userId;
    this.userToPromoteName = username;
    this.showPromotePopup = true;
  }

  closePromotePopup() {
    this.showPromotePopup = false;
    this.userToPromoteId = null;
    this.userToPromoteName = '';
  }

  confirmMakeAdmin() {
    if (this.userToPromoteId === null) return;

    this.http.put(`${environment.apiUrl}/users/make-admin`, {}, {
      withCredentials: true,
      params: { userId: this.userToPromoteId.toString() }
    }).subscribe({
      next: () => {
        this.loadStudents();
        this.closePromotePopup();
      },
      error: (err) => {
        this.closePromotePopup();
      }
    });
  }
}
