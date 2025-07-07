import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate } from '@angular/router';
import { ListadoVentasComponent } from './pages/listado-ventas/listado-ventas.component';
import { ListadoComprasComponent } from './pages/listado-compras.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { DetallePedidoVentaComponent } from './components/detalle-pedido-venta.component';
import { DetallePedidoCompraComponent } from './components/detalle-pedido-compra.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';
import { RoleGuard } from '../../guards/role.guard';
import { AuthGuard } from '../../guards/auth.guard';
import { ItemProductoClienteTiendaComponent } from './pages/item-producto-cliente-tienda/item-producto-cliente-tienda.component';
import { ItemProductoClienteOnlineComponent } from './pages/item-producto-cliente-online/item-producto-cliente-online.component';
import { ContactanosComponent } from '../compartido/contactanos/contactanos.component';
import { PedidoClienteFinalizadoComponent } from './components/pedido-finalizado/pedido-cliente-finalizado.component';

const routes: Routes = [
  // { path: '', redirectTo: ':clienteId', pathMatch: 'full' },

  {
    path: 'listado-ventas',
    component: ListadoVentasComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'listado-compras',
    component: ListadoComprasComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_COMPRAS' }
  },
  {
    path: 'rpte-pedidos/:tipoPedidoId',
    component: RptePedidoComponent
  },
  /*   { path: 'form/:clienteId',
      component: FormPedidoComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_CREATE_PEDIDO' }
    }, */
  {
    path: 'item-producto-cliente-tienda/:clienteId',
    component: ItemProductoClienteTiendaComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_CREATE_PEDIDO' }
  },

    {
    path: 'item-producto-cliente-online/:productoId',
    component: ItemProductoClienteOnlineComponent,
/*     canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_CREATE_PEDIDO' } */
  },

  {
    path: 'pedido-cliente-finalizado/:clienteId',
    component: PedidoClienteFinalizadoComponent,
/*     canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_CREATE_PEDIDO' } */
  },

    {
    path: 'contactanos',
    component: ContactanosComponent,
/*     canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_CREATE_PEDIDO' } */
  },


  {
    path: 'detalle-venta/:pedidoId',
    component: DetallePedidoVentaComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_VIEW_DETAILS_PEDIDO' }
  },
  {
    path: 'detalle-compra/:pedidoId',
    component: DetallePedidoCompraComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_VIEW_DETAILS_PEDIDO' }
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PedidosRoutingModule { }
