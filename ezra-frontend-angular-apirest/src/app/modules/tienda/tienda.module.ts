import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TiendaRoutingModule } from './tienda-routing.module';
import { CarritoComprasComponent } from './components/carrito-compras.component';
import { ProductosPorCategoriaComponent } from './pages/productos-por-categoria/productos-por-categoria.component';
import { ItemProductoTiendaComponent } from './pages/item-producto/item-producto-tienda.component';
import { PedidoTiendaComponent } from './pages/pedido-tienda/pedido-tienda.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CompartidoModule } from '../compartido/compartido.module';
import { ContactanosComponent } from './pages/contactanos/contactanos.component';


@NgModule({
  declarations: [
    CarritoComprasComponent,
    ProductosPorCategoriaComponent,
    ItemProductoTiendaComponent,
    PedidoTiendaComponent,
    ContactanosComponent
  ],
  imports: [
    CommonModule,
    TiendaRoutingModule,
    CompartidoModule
  ]
})
export class TiendaModule { }
