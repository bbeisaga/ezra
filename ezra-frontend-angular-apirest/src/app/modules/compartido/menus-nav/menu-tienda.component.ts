import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Output, Renderer2 } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Categoria } from '../../../models/categoria';
import { ProductoService } from '../../../services/producto.service';
import { SeoService } from '../../../services/seo.service';
import { environment } from '../../../../environments/environment';
import { CategoriaService } from '../../../services/categoria.service';

@Component({
  selector: 'menu-tienda',
  templateUrl: './menu-tienda.component.html',
  styleUrl: './menu-tienda.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule]
})
export class MenuTiendaComponent {
  private categoriaService = inject(CategoriaService)
  private router = inject(Router)

  lstCategoria: Categoria[] = []
  constructor(
    private renderer2: Renderer2
  ) {

  }

  ngOnInit(): void {
    //this.productoService.getAllProducto().subscribe(resp => this.lstProductos = resp);
    this.categoriaService.getCategoriasActivas().subscribe(
      resp => {
        this.lstCategoria = resp.sort((a, b) => a.orden - b.orden)
        //const nombresCategorias = this.lstCategoria.map(cat => cat.nombre)
        //this.seoCategoria(nombresCategorias.toString());
        /*       console.log("lstCategoria1", resp)
                    console.log("lstCategoria2", this.lstCategoria) */
        //const inicialCategoria = this.lstCategoria.filter(cat => cat.id == 0)[0];
        //this.outCategoria(inicialCategoria);

      }, err => { },
      () => {
        //la primera ves cargara el init de productos por categoria
        this.router.navigate(["/tienda/productos-categoria", 0], { queryParams: { categoria: 'tienda' } });
      });
  }

  /*   seoCategoria(nombresCategorias: string) {
      console.log("nombresCategorias", nombresCategorias)
      const tittle = "Categorias de productos para hacer publicidad merchndising y branding";
      const descripcion = `Las categorias son ${nombresCategorias} para hacer publicidad merchndising y branding`
      console.log("this.categoriaId()", tittle);
      console.log("this.descripcion()", descripcion);

      this.seo.title.setTitle(tittle);
      this.seo.meta.updateTag({ name: "description", content: descripcion })
      this.seo.meta.updateTag({ name: "keywords", content: nombresCategorias })
      this.seo.meta.updateTag({ property: "og:type", content: "website" })
      this.seo.meta.updateTag({ property: "og:description", content: descripcion })
      this.seo.meta.updateTag({ property: "og:url", content: `${environment.apiFront}/tienda` })
      this.seo.meta.updateTag({ property: "og:title", content: tittle })
      this.seo.meta.updateTag({ property: "og:image", content: `${environment.API_URL_VER_IMAGEN}${categoria.imagen}` })
      this.seo.serCanonicalURL(`${environment.apiFront}/tienda`)
      this.seo.setIndexFollow(true);
    } */

  emitCategoriaSubject(categoria: Categoria) {
    this.categoriaService.setCategoriaSubject(categoria);
  }
}
