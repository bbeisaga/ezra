import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import swal from 'sweetalert2';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AlertService } from '../services/alert.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService,
    private alertsService: AlertService,
    private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {


    return next.handle(req).pipe(
      catchError(e => {
       // let errorMessage = '';
        if (e instanceof ErrorEvent) {
          // client-side error
          //errorMessage = e.error.message;
          console.log(`AuthInterceptor.client-side error:`, e);
        }

        if (e instanceof HttpErrorResponse){
          console.log(`AuthInterceptor.httpResponse:`, e);
        } else {
          console.log(`AuthInterceptor.otherErrors:`, e);
        }

        if (e.status == 401) {
          if (this.authService.isAuthenticated()) {
            this.authService.logout();
          }
          this.router.navigate(['login']);
        }

        if (e.status == 403) {
          this.alertsService.warning(`Hola ${this.authService.usuario.username} no tienes acceso a este recurso!`,'Acceso denegado')
          //swal.fire('Acceso denegado', `Hola ${this.authService.usuario.username} no tienes acceso a este recurso!`, 'warning');
          //this.router.navigate(['/clientes']);
        }
        return throwError(e);
      })
    );
  }
}
