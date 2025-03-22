import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PedidosRoutingModule } from './pedidos-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { PedidosComponent } from './paginas/pedidos.component';
import { DetallePedidoComponent } from './components/detalle-pedido.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { RpteVentasComponent } from './reportes/rpte-ventas/rpte-ventas.component';

@NgModule({
  declarations: [
    FormPedidoComponent,
    PedidosComponent,
    DetallePedidoComponent,
    RpteVentasComponent
  ],
  imports: [
    CommonModule,
    PedidosRoutingModule,
    CompartidoModule
  ]
})
export class PedidosModule { }
