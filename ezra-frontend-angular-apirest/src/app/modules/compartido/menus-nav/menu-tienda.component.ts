import { CommonModule } from '@angular/common';
import { Component, Renderer2 } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Categoria } from '../../../models/categoria';
import { ProductoService } from '../../../services/producto.service';

@Component({
  selector: 'menu-tienda',
  templateUrl: './menu-tienda.component.html',
  styleUrl: './menu-tienda.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule]
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
