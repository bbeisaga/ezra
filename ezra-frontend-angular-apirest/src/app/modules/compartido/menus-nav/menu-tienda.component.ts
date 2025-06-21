import { Component, EventEmitter, OnInit, output, Output, Renderer2 } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { Categoria } from '../../../models/categoria';
import { Producto } from '../../../models/producto';

@Component({
  selector: 'menu-tienda',
  templateUrl: './menu-tienda.component.html',
  styleUrl: './menu-tienda.component.css'
})
export class MenuTiendaComponent {


  lstCategoria: Categoria[] = []
  categoria!: Categoria;

  constructor(private productoService: ProductoService,
              private renderer2: Renderer2
  ) {

  }

  ngOnInit(): void {
    //this.productoService.getAllProducto().subscribe(resp => this.lstProductos = resp);
    this.productoService.getCategoriasProducto().subscribe(resp => this.lstCategoria = resp.sort((a, b) => a.orden - b.orden))
  }
}
