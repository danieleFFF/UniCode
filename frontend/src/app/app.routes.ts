import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login').then(m => m.Login)
  },
  {
    path: '',
    loadComponent: () => import('./pages/home/home').then(m => m.Home),
    pathMatch: 'full'
  },
  { path: 'exercises',
    loadComponent: () => import('./pages/exercises/exercises.component').then(m => m.ExercisesComponent)
  },
  {
    path: 'ranking',
    loadComponent: () => import('./pages/ranking/ranking').then(m => m.Ranking)
  },
  {
    path: 'userBoard',
    loadComponent: () => import('./pages/UserSetting&&Board/user&Board').then(m => m.UserBoard)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register').then(m => m.Register)
  },

  {
    path: '**',
    redirectTo: ''
  }
];
