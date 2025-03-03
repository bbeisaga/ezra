import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';

import { AuthService } from './auth.service';
import { Caja } from '../models/caja';
import { CajaUsuario } from '../models/caja-usuario';
import { Usuario } from '../models/usuario';
import { Movimiento } from '../models/movimiento';
import { TipoMovimiento } from '../models/tipo-movimiento';
import { TipoPago } from '../models/tipo-pago';
import { MovimientoCaja } from '../models/movimiento-caja';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovimientoService {

 // private urlEndPoint: string = 'http://localhost:8080/api/movimientos';

  //private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient,
              private authService: AuthService ) { }

/*   private agregarAuthorizationHeader(){
    let token = this.authService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer ' + token)
    }
    return this.httpHeaders
  } */


  getAllTipoPagos(): Observable<TipoPago[]> {
    return this.http.get<TipoPago[]>(`${environment.apiUrl}/movimientos/tipoPagos`
      /*, {headers: this.agregarAuthorizationHeader()} */
    );
  }

  getAllTipoMovimientos(): Observable<TipoMovimiento[]> {
      return this.http.get<TipoMovimiento[]>(`${environment.apiUrl}/movimientos/tipoMovimientos`
      /*  , {headers: this.agregarAuthorizationHeader()} */
      );
  }

  createMovimiento(movimiento:Movimiento): Observable <Movimiento> {
    return this.http.post(`${environment.apiUrl}/movimientos`, movimiento
      /*, {headers: this.agregarAuthorizationHeader()}*/
    )
        .pipe(
          map((response: any) => response.movimiento as Movimiento),
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
    return this.http.post(`${environment.apiUrl}/movimientos/caja`, movimientoCaja
      /*, {headers: this.agregarAuthorizationHeader()}*/
    )
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
