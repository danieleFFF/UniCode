import { Routes } from '@angular/router';
import {ExercisesComponent} from './pages/exercises/exercises.component';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./login/login').then(m => m.Login)
  },
  {
    path: '',
    loadComponent: () => import('./home/home').then(m => m.Home),
    pathMatch: 'full'
  },
  { path: 'exercises', component: ExercisesComponent },
  {
    path: '**',
    redirectTo: ''
  }

];
