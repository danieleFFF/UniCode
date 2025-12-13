import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { of } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);


  if (!isPlatformBrowser(platformId)) {
    console.log(`[Server] Blocco richiesta auth verso ${req.url}`);
    return of(new HttpResponse({ body: null, status: 200 }));
  }

  const clonedRequest = req.clone({ withCredentials: true })

  return next(clonedRequest);
};
