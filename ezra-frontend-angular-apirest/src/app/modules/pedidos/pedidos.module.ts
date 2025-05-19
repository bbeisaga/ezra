import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PedidosRoutingModule } from './pedidos-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { DetallePedidoVentaComponent } from './components/detalle-pedido-venta.component';
import { DetallePedidoCompraComponent } from './components/detalle-pedido-compra.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';
import { DetalleItemPedidoComponent } from './components/detalle-item-pedido/detalle-item-pedido.component';
import { ListadoVentasComponent } from './pages/listado-ventas.component';
import { ListadoComprasComponent } from './pages/listado-compras.component';

@NgModule({
  declarations: [
    FormPedidoComponent,
    ListadoVentasComponent,
    ListadoComprasComponent,
    DetallePedidoVentaComponent,
    DetallePedidoCompraComponent,
    RptePedidoComponent,
    DetalleItemPedidoComponent,
  ],
  imports: [
    CommonModule,
    PedidosRoutingModule,
    CompartidoModule
  ]
})
export class PedidosModule { }
