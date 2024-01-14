import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, throwError } from "rxjs";

export const erroInterceptor: HttpInterceptorFn = (req, next) => {
  const router: Router = inject(Router);
  return next(req).pipe(
    catchError(error => {
      router.navigateByUrl('/')
      return throwError(() => error);
    })
  )
}