import { inject, PLATFORM_ID } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { Observable, of } from 'rxjs';
import { map, catchError, take } from 'rxjs/operators';
import { UserService } from '../services/user.service';

export const authGuard = (): Observable<boolean | UrlTree> | boolean | UrlTree => {
    const userService = inject(UserService);
    const router = inject(Router);
    const platformId = inject(PLATFORM_ID);


    if (!isPlatformBrowser(platformId)) {
        return true;
    }

    if (userService.getCurrentUser()) {
        return true;
    }


    return userService.getProfile().pipe(
        take(1),
        map(user => {
            if (user) {
                return true;
            }
            return router.createUrlTree(['/login']);
        }),
        catchError(() => {
            return of(router.createUrlTree(['/login']));
        })
    );
};
