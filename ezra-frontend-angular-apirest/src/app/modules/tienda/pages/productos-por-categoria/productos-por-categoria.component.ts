import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Producto } from '../../../../models/producto';
import { ProductoService } from '../../../../services/producto.service';


@Component({
  selector: 'app-productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css'
})
export class ProductosPorCategoriaComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  lstProductos: Producto[]=[];

  constructor(){}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params=> {
      let categoriaId = + params.get('catId')!;
      this.productoService.productosPorCategoria(categoriaId).subscribe(resp => {
          this.lstProductos = resp;
      })
    })
  }

}
