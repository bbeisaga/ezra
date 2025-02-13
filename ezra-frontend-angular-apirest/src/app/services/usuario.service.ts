import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente';
import { Region } from '../models/region';
import { HttpClient, HttpRequest, HttpEvent, HttpHeaders, HttpParams } from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { TipoDocumento } from '../models/tipo-documento';
import { PageableParams } from '../models/pageable-params';
import { PageableResponse } from '../models/pageable-response';
import { Usuario } from '../models/usuario';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  //private urlEndPoint: string = 'http://localhost:8080/api/usuarios';

  /* private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})


  private agregarAuthorizationHeader(){
    let token = this.authService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer ' + token)
    }
    return this.httpHeaders
  } */

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}


  getUsuarioByUsername(username: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${environment.apiUrl}/usuarios/${username}`
/*       , {headers: this.agregarAuthorizationHeader()}
 */    )
/*     .pipe(
      catchError(e => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/clientes']);
          console.error(e.error.mensaje);
        }

        return throwError(e);
      })); */
  }
}
