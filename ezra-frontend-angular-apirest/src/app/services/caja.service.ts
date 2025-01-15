import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

import { AuthService } from './auth.service';
import { Caja } from '../models/caja';
import { CajaUsuario } from '../models/caja-usuario';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class CajaService {

  private urlEndPoint: string = 'http://localhost:8080/api/cajas';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient,
              private authService: AuthService ) { }

  private agregarAuthorizationHeader(){
    let token = this.authService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer ' + token)
    }
    return this.httpHeaders
  }

  getAllCaja(): Observable<Caja[]> {
    return this.http.get<Caja[]>(`${this.urlEndPoint}`, {headers: this.agregarAuthorizationHeader()} );
  }

  getCajaUsuarioByUserName(user: Usuario): Observable<CajaUsuario>{
    return this.http.get<CajaUsuario>(`${this.urlEndPoint}/usuarios/${user.username}`, {headers: this.agregarAuthorizationHeader()} );

  }

  getCajaUsuarioByCajaIdAndUserId(cajaId:number, userId: number): Observable<CajaUsuario>{
    return this.http.get<CajaUsuario>(`${this.urlEndPoint}/${cajaId}/usuarios/${userId}`, {headers: this.agregarAuthorizationHeader()} );

  }

  create(cajaUsuario: CajaUsuario): Observable<any> {
    return this.http.post<any>(`${this.urlEndPoint}/usuarios`, cajaUsuario, {headers: this.agregarAuthorizationHeader()});
  }

  update(cajaUsuario: CajaUsuario): Observable<any> {
    return this.http.put<any>(`${this.urlEndPoint}/usuarios/${cajaUsuario.id}`, cajaUsuario, {headers: this.agregarAuthorizationHeader()}).pipe(
      catchError(e => {
        if (e.status == 400) {
          return throwError(e);
        }
        if (e.error.mensaje) {
          console.error(e.error.mensaje);
        }
        return throwError(e);
      }));
  }

/*   getPedido(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.urlEndPoint}/${id}`, {headers: this.agregarAuthorizationHeader()} );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlEndPoint}/${id}`, {headers: this.agregarAuthorizationHeader()});
  }

  filtrarProductos(term: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.urlEndPoint}/filtrar-productos/${term}`, {headers: this.agregarAuthorizationHeader()});
  }*/




}
