import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PedidosRoutingModule } from './pedidos-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { PedidosComponent } from './paginas/pedidos.component';
import { DetallePedidoComponent } from './components/detalle-pedido.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';
import { DetalleItemPedidoComponent } from './components/detalle-item-pedido/detalle-item-pedido.component';

@NgModule({
  declarations: [
    FormPedidoComponent,
    PedidosComponent,
    DetallePedidoComponent,
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
