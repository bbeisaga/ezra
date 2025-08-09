import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { Categoria } from '../models/categoria';
import { Producto } from '../models/producto';
import { AlertService } from './alert.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  //public meta: Meta = inject(Meta);
  //public title: Title = inject(Title);
  //private _document: Document = inject(DOCUMENT);

  //private _producto!: Producto
  categoriaSubject = new Subject<Categoria>();

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

  /*   setProducto(producto: Producto) {
      this._producto = producto;
    }

    get producto() {
      return { ...this._producto }
    } */

  /*   setProductoToSeo(producto: Producto) {
      this.title.setTitle(`${producto.codigo} - ${producto.nombre} `)
      this.meta.updateTag({ name: "description", content: `${producto.descripcion}` })
      this.meta.updateTag({ name: "og:description", content: `${producto.descripcion}` })
      this.meta.updateTag({ name: "keywords", content: `${producto.categoria?.nombre}` })
      this.meta.updateTag({ name: "og:url", content: `${environment.apiFront}/pedidos/item-producto-cliente-online/${producto.id}` })
      this.meta.updateTag({ name: "og:title", content: `${producto.codigo} - ${producto.nombre}` })
      this.meta.updateTag({ name: "og:image", content: `${environment.API_URL_VER_IMAGEN}${producto.imagen}` })
      this.meta.updateTag({ name: "robots", content: "index, follow" })

    } */


  setCategoriaSubject(categoria: Categoria) {
    this.categoriaSubject.next(categoria);
  }

  getCategoriaSubject() {
    return this.categoriaSubject.asObservable();
  }

  getCategoriasProducto(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(environment.apiUrl + '/producto/categorias');
  }

  getCategoriasActivas(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(environment.apiUrl + '/categorias/active');
  }


  createCategoria(categoria: Categoria): Observable<Categoria> {
    return this.http.post(`${environment.apiUrl}/categoria`, categoria)
      .pipe(
        map((response: any) => response.categoria as Categoria),
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


  updateCategoria(categoria: Categoria): Observable<Categoria> {
    return this.http.put<any>(`${environment.apiUrl}/categoria/${categoria.id}`, categoria)
      .pipe(
        map((response: any) => response.categoria as Categoria),
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
