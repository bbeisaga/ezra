import { CategoriaService } from './../../../services/categoria.service';
import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { COLOR_ESTADO_PRODUCTO } from '../../../constants/color-estado-producto';
import { Producto } from '../../../models/producto';
import { ChatUtils } from '../../../utils/chat-utils';
import { AngularMaterialModule } from '../../compartido/angular-material.module';
import { ProductoService } from './../../../services/producto.service';
import { SeoService } from './../../../services/seo.service';
import { toSignal } from '@angular/core/rxjs-interop'
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, AngularMaterialModule],
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class ProductosPorCategoriaComponent implements OnInit, OnDestroy {
  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private seo = inject(SeoService);
  categoriaSuscription$!: Subscription;

  lstProductos: Producto[] = [];
  chatUtils = ChatUtils;
  //public categoriaId = toSignal<number>(this.activatedRoute.paramMap.pipe(map(r => + (r.get('categoriaId') ?? '0'))));
  //public categoriaName = toSignal<string>(this.activatedRoute.queryParamMap.pipe(map(r => r.get('categoria') ?? 'tienda')));

  constructor(private cdr: ChangeDetectorRef) {
    this.categoriaSuscription$ = this.categoriaService.getCategoriaSubject().subscribe(
      resp => {
        this.loadPorductosPorCategoria(resp.id, resp.nombre);
      })
  }

  ngOnInit(): void {
    const categoriaId = + this.activatedRoute.snapshot.params['categoriaId'];
    const categoriaName = this.activatedRoute.snapshot.queryParamMap.get('categoria');
    //console.log("ngOnInit.categoriaId", this.categoriaId());
    //console.log("ngOnInit.categoriaName", this.categoriaName()!.toString());
    //this.loadPorductosPorCategoria(Number(this.categoriaId()), this.categoriaName()!.toString());
    this.loadPorductosPorCategoria(categoriaId, categoriaName!);

  }

  loadPorductosPorCategoria(categoriaId: number, categoriaName: string = 'tienda') {
    //console.log("this.categoriaId()", categoria.id);
    this.productoService.productosPorCategoria(categoriaId)
      .subscribe(resp => {
        this.lstProductos = resp.map(prd => {
          prd.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
          prd.imagen = environment.API_URL_VER_IMAGEN + prd.imagen;
          prd.precioNetoStringShow = "S/ ".concat(prd.margenesProducto.map(m => m.precioNeto).toString().replaceAll(',', ' - '));
          return prd;
        })
        //this.productoService.setProductosBehaviorSubject(this.lstProductos);
        console.log("this.lstProductos", this.lstProductos);
        this.cdr.detectChanges();
        this.seoProductosPorCategoria(categoriaId.toString(), categoriaName, this.lstProductos);
      })
  }

  seoProductosPorCategoria(categoriaId: string, categoriaName: string, lstProductos: Producto[]) {
    const productos = lstProductos.map(pr => pr.nombre).toString();
    const tittle = `${categoriaId} - ${categoriaName}`;
    const descripcion = `En la categoria ${categoriaName} estan ${productos}`
    this.seo.title.setTitle(tittle)
    this.seo.meta.updateTag({ name: "description", content: descripcion })
    this.seo.meta.updateTag({ name: "keywords", content: `${productos}` })
    this.seo.meta.updateTag({ property: "og:type", content: "website" })
    this.seo.meta.updateTag({ property: "og:description", content: descripcion })
    this.seo.meta.updateTag({ property: "og:url", content: `${environment.apiFront}/tienda/productos-categoria/${categoriaId}` })
    this.seo.meta.updateTag({ property: "og:title", content: tittle })
    //this.seo.meta.updateTag({ property: "og:image", content: `${environment.API_URL_VER_IMAGEN}${categoria.imagen}` })
    this.seo.serCanonicalURL(`${environment.apiFront}/tienda/productos-categoria`)
    this.seo.setIndexFollow(true);
  }


  chatear(producto: Producto) {
    this.chatUtils.infoProduct(producto);
  }

  ngOnDestroy(): void {
    if (this.categoriaSuscription$) {
      this.categoriaSuscription$.unsubscribe();
    }
  }

}
