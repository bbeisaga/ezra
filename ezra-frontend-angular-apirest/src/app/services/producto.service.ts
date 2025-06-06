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
import { Categoria } from '../models/categoria';
import { Color } from '../models/color';
import { Empaque } from '../models/empaque';
import { Material } from '../models/material';
import { Origen } from '../models/origen';
import { Producto } from '../models/producto';
import { Uso } from '../models/uso';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  //private httpHeaders = new HttpHeaders()


  constructor(private http: HttpClient,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService) { }

  /*   private agregarAuthorizationHeader(){
        let token = this.authService.token;
        if(token != null){
          return this.httpHeaders.append('Authorization', 'Bearer ' + token)
        }
        return this.httpHeaders
      } */

  getCategoriasProducto(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(environment.apiUrl + '/producto/categorias');
  }

  getColoresProducto(): Observable<Color[]> {
    return this.http.get<Color[]>(`${environment.apiUrl}/producto/colores`
    );
  }

  /*   getEmpaquesProducto(): Observable<Empaque[]> {
      return this.http.get<Empaque[]>(`${environment.apiUrl}/producto/empaques`
      );
    } */

  getMaterialesProducto(): Observable<Material[]> {
    return this.http.get<Material[]>(`${environment.apiUrl}/producto/materiales`
    );
  }

  /*   getOrigenesProducto(): Observable<Origen[]> {
      return this.http.get<Origen[]>(`${environment.apiUrl}/producto/origenes`
      );
    } */

  getUsosInternoProducto(): Observable<Uso[]> {
    return this.http.get<Uso[]>(`${environment.apiUrl}/producto/usos`
    );
  }

  getAllProductosPageable(params: any): Observable<PageableResponse> {
    return this.http.get<any>(`${environment.apiUrl}/producto/pageable`, {
      params: params,
    });
  }

  getAllProducto(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${environment.apiUrl}/productos`);
  }

  filtrarProductos(term: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${environment.apiUrl}/producto/filtrar-productos/${term}`
    );
  }

  /*   getClientes(page: number): Observable<any> {
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
    } */

  createProducto(producto: Producto): Observable<Producto> {
    return this.http.post(`${environment.apiUrl}/producto`, producto
      /*,{headers: this.agregarAuthorizationHeader()}*/
    )
      .pipe(
        map((response: any) => response.prodcuto as Producto),
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

  createProductoImagen(formData: FormData): Observable<Producto> {
    let httpHeaders = new HttpHeaders()
    let token = this.authService.token;
    if (token != null) {
      httpHeaders.append('Authorization', 'Bearer ' + token)
    }

    return this.http.post(`${environment.apiUrl}/producto/imagen`, formData
      , { headers: httpHeaders }
    )
      .pipe(
        map((response: any) => response.producto as Producto),
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

    updateProductoImagen(formData: FormData, productoId: number): Observable<Producto> {
    let httpHeaders = new HttpHeaders()
    let token = this.authService.token;
    if (token != null) {
      httpHeaders.append('Authorization', 'Bearer ' + token)
    }

    return this.http.put<Producto>(`${environment.apiUrl}/producto/imagen/${productoId}`, formData, { headers: httpHeaders })
      .pipe(
        map((response: any) => response.producto as Producto),
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

  getProducto(id: any): Observable<Producto> {
    return this.http.get<Producto>(`${environment.apiUrl}/producto/${id}`
      /*, {headers: this.agregarAuthorizationHeader()}*/
    ).pipe(
      catchError(e => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/productos']);
          console.error(e.error.mensaje);
        }

        return throwError(e);
      }));
  }

  updateProducto(producto: Producto): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/producto/${producto.id}`, producto
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

  deleteProducto(id: number): Observable<Producto> {
    return this.http.delete<Producto>(`${environment.apiUrl}/producto/${id}`
      /*, {headers: this.agregarAuthorizationHeader()}*/
    ).pipe(
      catchError(e => {
        if (e.error.mensaje) {
          console.error(e.error.mensaje);
        }
        return throwError(e);
      }));
  }

  subirImagen(archivo: File, productoId: any): Observable<Producto> {
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("productoId", productoId);

    /*     const req = new HttpRequest('POST', `${environment.apiUrl}/clientes/upload`, formData, {
          reportProgress: true
        }); */

    return this.http.post<any>(`${environment.apiUrl}/upload-imagen/`, formData).pipe(
      map(response => response.producto as Producto),
      catchError(e => {
        if (e.error.mensaje) {
          this.alertService.error(e.error.mensaje, e.error.err);
        }
        return throwError(e);
      })
    )
  }
}
