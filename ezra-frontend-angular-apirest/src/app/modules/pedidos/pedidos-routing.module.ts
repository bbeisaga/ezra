import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PedidosComponent } from './paginas/pedidos.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { DetallePedidoComponent } from './components/detalle-pedido.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';

const routes: Routes = [
 // { path: '', redirectTo: ':clienteId', pathMatch: 'full' },

  { path: '', component: PedidosComponent , pathMatch: 'full'},
  { path: 'rpte-pedidos/:tipoPedidoId', component: RptePedidoComponent },
  { path: 'form/:clienteId', component: FormPedidoComponent },
  { path: 'detalle/:pedidoId', component: DetallePedidoComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PedidosRoutingModule { }
