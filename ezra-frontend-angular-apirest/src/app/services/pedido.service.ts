import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Pedido } from '../models/pedido';
import { Producto } from '../models/producto';
import { AuthService } from './auth.service';
import { EstadoPedido } from '../models/estado-pedido';
import { PageableResponse } from '../models/pageable-response';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  private _pedido!:Pedido

  private urlEndPoint: string = 'http://localhost:8080/api/pedidos';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient, private authService: AuthService ) { }

  private agregarAuthorizationHeader(){
    let token = this.authService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer ' + token)
    }
    return this.httpHeaders
  }

  setPedido (pedido: Pedido){
    this._pedido = pedido
  }

  get pedido(){
    return {...this._pedido}
  }

  getAllPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.urlEndPoint}`, {headers: this.agregarAuthorizationHeader()} );
  }

  getAllPedidosPageable(params: any): Observable<PageableResponse> {
    return this.http.get<any>(`${this.urlEndPoint}/pageable`, {
      headers: this.agregarAuthorizationHeader(),
      params : params,
      } );
  }

  getAllEstadoPedido(): Observable<EstadoPedido[]>{
    return this.http.get<EstadoPedido[]>(`${this.urlEndPoint}/estado-pedido`, {headers: this.agregarAuthorizationHeader()} );

  }

  getPedido(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.urlEndPoint}/${id}`, {headers: this.agregarAuthorizationHeader()} );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlEndPoint}/${id}`, {headers: this.agregarAuthorizationHeader()});
  }

  filtrarProductos(term: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.urlEndPoint}/filtrar-productos/${term}`, {headers: this.agregarAuthorizationHeader()});
  }

  create(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(this.urlEndPoint, pedido, {headers: this.agregarAuthorizationHeader()});
  }

  update(pedido: Pedido): Observable<any> {
    return this.http.put<any>(`${this.urlEndPoint}/${pedido.id}`, pedido, {headers: this.agregarAuthorizationHeader()}).pipe(
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
