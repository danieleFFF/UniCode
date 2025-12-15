import { Routes } from '@angular/router';
import { SolveComponent } from './pages/solve/solve.component';
import { ExercisesComponent } from './pages/exercises/exercises.component';
import { authGuard } from './guards/auth.guard';

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
  {
    path: 'exercises',
    loadComponent: () => import('./pages/exercises/exercises.component').then(m => m.ExercisesComponent)
  },

  {
    path: 'courses/:language',
    loadComponent: () => import('./pages/courses/courses').then(m => m.Courses)
  },

  {
    path: 'courses-home',
    loadComponent: () => import('./pages/courses-home/courses-home').then(m => m.CoursesHome)
  },

  {
    path: 'courses/:language/topic/:topicIndex',
    loadComponent: () => import('./pages/theory/theory').then(m => m.Theory)
  },

  {
    path: 'ranking',
    loadComponent: () => import('./pages/ranking/ranking').then(m => m.Ranking),
    canActivate: [authGuard]
  },

  {
    path: 'userBoard',
    loadComponent: () => import('./pages/UserSettingAndBoard/userAndBoard').then(m => m.UserAndBoard),
    canActivate: [authGuard]
  },

  {
    path: 'register',
    loadComponent: () => import('./pages/register/register').then(m => m.Register)
  },

  { path: '', component: ExercisesComponent },
  {
    path: 'solve/:id',
    component: SolveComponent,
    canActivate: [authGuard]
  },

  {
    path: 'password-recover',
    loadComponent: () => import('./pages/password-recover/password-recover').then(m => m.PasswordRecover)
  },

  {
    path: '**',
    redirectTo: ''
  }
];
