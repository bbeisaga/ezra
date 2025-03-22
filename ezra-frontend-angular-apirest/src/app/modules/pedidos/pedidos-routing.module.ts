import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PedidosComponent } from './paginas/pedidos.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { DetallePedidoComponent } from './components/detalle-pedido.component';
import { RpteVentasComponent } from './reportes/rpte-ventas/rpte-ventas.component';

const routes: Routes = [
 // { path: '', redirectTo: ':clienteId', pathMatch: 'full' },

  { path: '', component: PedidosComponent , pathMatch: 'full'},
  { path: 'rpteventas', component: RpteVentasComponent },
  { path: 'form/:clienteId', component: FormPedidoComponent },
  { path: ':pedidoId', component: DetallePedidoComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PedidosRoutingModule { }
