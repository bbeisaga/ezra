import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';

import { AuthService } from './auth.service';
import { Caja } from '../models/caja';
import { CajaUsuario } from '../models/caja-usuario';
import { Usuario } from '../models/usuario';
import { MovimientoVenta } from '../models/movimiento-venta';
import { TipoMovimiento } from '../models/tipo-movimiento';
import { TipoPago } from '../models/tipo-pago';
import { MovimientoCaja } from '../models/movimiento-caja';

@Injectable({
  providedIn: 'root'
})
export class MovimientoService {

  private urlEndPoint: string = 'http://localhost:8080/api/movimientos';

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


  getAllTipoPagos(): Observable<TipoPago[]> {
    return this.http.get<TipoPago[]>(`${this.urlEndPoint}/tipoPagos`, {headers: this.agregarAuthorizationHeader()} );
  }

  getAllTipoMovimientos(): Observable<TipoMovimiento[]> {
      return this.http.get<TipoMovimiento[]>(`${this.urlEndPoint}/tipoMovimientos`, {headers: this.agregarAuthorizationHeader()} );
  }

  createMovimiento(movimientoVenta:MovimientoVenta): Observable <MovimientoVenta> {
    return this.http.post(this.urlEndPoint, movimientoVenta, {headers: this.agregarAuthorizationHeader()})
        .pipe(
          map((response: any) => response.movimientoVenta as MovimientoVenta),
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

  createMovimientoCaja(movimientoCaja:MovimientoCaja): Observable <MovimientoCaja> {
    return this.http.post(`${this.urlEndPoint}/caja`, movimientoCaja, {headers: this.agregarAuthorizationHeader()})
        .pipe(
          map((response: any) => response.movimientoCaja as MovimientoCaja),
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






}
