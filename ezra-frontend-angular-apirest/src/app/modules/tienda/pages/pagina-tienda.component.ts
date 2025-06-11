import { ProductoService } from './../../../services/producto.service';
import { Component, inject, OnInit } from '@angular/core';
import { Producto } from '../../../models/producto';
import { Router } from '@angular/router';
import { Categoria } from '../../../models/categoria';

@Component({
  selector: 'app-pagina-tienda',
  templateUrl: './pagina-tienda.component.html',
  styleUrl: './pagina-tienda.component.css'
})
export class PaginaTiendaComponent implements OnInit  {

  private router = inject(Router);

  ngOnInit(): void {
    //navegara al boton INICIO o HOME
    this.router.navigate(["/tienda/productos-categoria",0])
  }
}
