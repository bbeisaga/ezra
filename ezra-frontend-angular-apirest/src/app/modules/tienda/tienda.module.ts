import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TiendaRoutingModule } from './tienda-routing.module';
import { CarritoComprasComponent } from './components/carrito-compras.component';
import { PasarelaPagoComponent } from './components/pasarela-pago.component';
import { ProductosPorCategoriaComponent } from './pages/productos-por-categoria/productos-por-categoria.component';
import { ItemProductoTiendaComponent } from './pages/item-producto/item-producto-tienda.component';
import { PedidoTiendaComponent } from './pages/pedido-tienda/pedido-tienda.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CompartidoModule } from '../compartido/compartido.module';


@NgModule({
  declarations: [
    CarritoComprasComponent,
    PasarelaPagoComponent,
    ProductosPorCategoriaComponent,
    ItemProductoTiendaComponent,
    PedidoTiendaComponent,
  ],
  imports: [
    CommonModule,
    TiendaRoutingModule,
    CompartidoModule
  ]
})
export class TiendaModule { }
