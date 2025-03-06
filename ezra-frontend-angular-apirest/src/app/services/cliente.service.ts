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
import { environment } from '../../environments/environment';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  //private urlEndPoint: string = 'http://localhost:8080/api/clientes';

  //private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient,
    private router: Router,
    private alertService: AlertService,
    private authService: AuthService) {}

/*   private agregarAuthorizationHeader(){
    let token = this.authService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer ' + token)
    }
    return this.httpHeaders
  } */

  getTipoDocumento(): Observable<TipoDocumento[]> {
    return this.http.get<TipoDocumento[]>(environment.apiUrl + '/clientes/documentos'
      /*,{headers: this.agregarAuthorizationHeader()}*/
    );
  }

  getAllClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${environment.apiUrl}/clientes`
      /*,{headers: this.agregarAuthorizationHeader()}*/
    );
  }

  getAllClientesPageable(params: any): Observable<PageableResponse> {

    return this.http.get<any>(`${environment.apiUrl}/clientes/pageable`,{
     /* headers: this.agregarAuthorizationHeader(),*/
      params : params,
    });


/*     return this.http.post<PageableResponse>(`${this.urlEndPoint}/pageable`  ,{headers: this.agregarAuthorizationHeader(), params : params});
 */
  }

  getClientes(page: number): Observable<any> {
    return this.http.get(`${environment.apiUrl}/clientes` + '/page/' + page).pipe(
      tap((response: any) => {
        console.log('ClienteService: tap 1');
        (response.content as Cliente[]).forEach(cliente => console.log(cliente.nombres));
      }),
      map((response: any) => {
        (response.content as Cliente[]).map(cliente => {
          cliente.nombres = cliente.nombres.toUpperCase();
          return cliente;
        });
        return response;
      }),
      tap(response => {
        console.log('ClienteService: tap 2');
        (response.content as Cliente[]).forEach(cliente => console.log(cliente.nombres));
      }));
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post(`${environment.apiUrl}/clientes`, cliente
    )
      .pipe(
        map((response: any) => response.cliente as Cliente),
        catchError(e => {
 /*          if (e.status == 400) {
            return throwError(e);
          } */
          if (e.error.mensaje) {
            this.alertService.error(e.error.mensaje,e.error.err);
          }
          return throwError(e);
        }));
  }

  getCliente(id: any): Observable<Cliente> {
    return this.http.get<Cliente>(`${environment.apiUrl}/clientes/${id}`
      /*, {headers: this.agregarAuthorizationHeader()}*/
    ).pipe(
      catchError(e => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/clientes']);
          console.error(e.error.mensaje);
        }

        return throwError(e);
      }));
  }

  update(cliente: Cliente): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/clientes/${cliente.id}`, cliente
      /*, {headers: this.agregarAuthorizationHeader()}*/
    ).pipe(
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

  delete(id: number): Observable<Cliente> {
    return this.http.delete<Cliente>(`${environment.apiUrl}/clientes/${id}`
      /*, {headers: this.agregarAuthorizationHeader()}*/
    ).pipe(
      catchError(e => {
        if (e.error.mensaje) {
          console.error(e.error.mensaje);
        }
        return throwError(e);
      }));
  }

  subirFoto(archivo: File, id: any): Observable<HttpEvent<{}>> {
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", id);

    const req = new HttpRequest('POST', `${environment.apiUrl}/clientes/upload`, formData, {
      reportProgress: true
    });

    return this.http.request(req);
  }
}
