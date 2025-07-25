import { CarritoItemProductoComponent } from './../../components/carrito-item-producto/carrito-item-producto.component';
import { CustomizeItemProductoToClientComponent } from './../../components/customize-item-producto-to-client/customize-item-producto-to-client.component';


import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Producto } from '../../../../models/producto';
import { AuthService } from '../../../../services/auth.service';
import { ProductoService } from '../../../../services/producto.service';
import { AngularMaterialModule } from '../../../compartido/angular-material.module';

@Component({
  selector: 'item-producto-cliente-online',
  templateUrl: './item-producto-cliente-online.component.html',
  styleUrl: './item-producto-cliente-online.component.css',
  standalone: true,
    imports: [CarritoItemProductoComponent, CustomizeItemProductoToClientComponent, CommonModule, RouterModule, FormsModule, ReactiveFormsModule, AngularMaterialModule ]

})
export class ItemProductoClienteOnlineComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  public authService = inject(AuthService);
  producto!: Producto;
  constructor() { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const productoId = + params.get('productoId')!;
      this.productoService.getProducto(productoId).subscribe(prd => {
        this.producto = prd;

      })
    })
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }


}
