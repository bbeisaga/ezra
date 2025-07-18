import { CarritoItemProductoComponent } from './../../components/carrito-item-producto/carrito-item-producto.component';
import { CustomizeItemProductoToClientComponent } from './../../components/customize-item-producto-to-client/customize-item-producto-to-client.component';


import { Component, inject, OnInit } from '@angular/core';
import { Producto } from '../../../../models/producto';
import { AuthService } from '../../../../services/auth.service';
import { ProductoService } from '../../../../services/producto.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'item-producto-cliente-online',
  templateUrl: './item-producto-cliente-online.component.html',
  styleUrl: './item-producto-cliente-online.component.css',
  standalone: true,
    imports: [CarritoItemProductoComponent, CustomizeItemProductoToClientComponent, CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

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