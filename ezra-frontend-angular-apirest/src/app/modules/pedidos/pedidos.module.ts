import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PedidosRoutingModule } from './pedidos-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { DetallePedidoVentaComponent } from './components/detalle-pedido-venta.component';
import { DetallePedidoCompraComponent } from './components/detalle-pedido-compra.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';
import { DetalleItemPedidoComponent } from './components/detalle-item-pedido/detalle-item-pedido.component';
import { ListadoVentasComponent } from './pages/listado-ventas/listado-ventas.component';
import { ListadoComprasComponent } from './pages/listado-compras.component';
import { CustomizeItemProductoToClientComponent } from './components/customize-item-producto-to-client/customize-item-producto-to-client.component';
import { PedidoFinalizadoComponent } from './components/pedido-finalizado/pedido-finalizado.component';
import { ItemProductoClienteTiendaComponent } from './pages/item-producto-cliente-tienda/item-producto-cliente-tienda.component';
import { ItemProductoClienteOnlineComponent } from './pages/item-producto-cliente-online/item-producto-cliente-online.component';
import { CarritoItemProductoComponent } from './components/carrito-item-producto/carrito-item-producto.component';

@NgModule({
  declarations: [
    FormPedidoComponent,
    ListadoVentasComponent,
    ListadoComprasComponent,
    DetallePedidoVentaComponent,
    DetallePedidoCompraComponent,
    RptePedidoComponent,
    DetalleItemPedidoComponent,
    ItemProductoClienteTiendaComponent,
    ItemProductoClienteOnlineComponent,
    CustomizeItemProductoToClientComponent,
    PedidoFinalizadoComponent,
    CarritoItemProductoComponent
  ],
  imports: [
    CommonModule,
    PedidosRoutingModule,
    CompartidoModule
  ]
})
export class PedidosModule { }
