import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate } from '@angular/router';
import { ListadoVentasComponent } from './pages/listado-ventas.component';
import { ListadoComprasComponent } from './pages/listado-compras.component';
import { FormPedidoComponent } from './components/form-pedido.component';
import { DetallePedidoVentaComponent } from './components/detalle-pedido-venta.component';
import { DetallePedidoCompraComponent } from './components/detalle-pedido-compra.component';
import { RptePedidoComponent } from './reportes/rpte-pedido/rpte-pedido.component';
import { RoleGuard } from '../../guards/role.guard';
import { AuthGuard } from '../../guards/auth.guard';

const routes: Routes = [
 // { path: '', redirectTo: ':clienteId', pathMatch: 'full' },

  { path: 'listado-ventas',
    component: ListadoVentasComponent,
    canActivate:[AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_VENTAS' }
  },
  { path: 'listado-compras',
    component: ListadoComprasComponent,
    canActivate:[AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_COMPRAS' }
  },
  { path: 'rpte-pedidos/:tipoPedidoId',
    component: RptePedidoComponent
  },
  { path: 'form/:clienteId',
    component: FormPedidoComponent,
    canActivate:[AuthGuard, RoleGuard],
    data: { role: 'ROLE_CREATE_PEDIDO' }
  },
  { path: 'detalle-venta/:pedidoId',
    component: DetallePedidoVentaComponent,
    canActivate:[AuthGuard, RoleGuard],
    data: { role: 'ROLE_VIEW_DETAILS_PEDIDO' }
  },
    { path: 'detalle-compra/:pedidoId',
    component: DetallePedidoCompraComponent,
    canActivate:[AuthGuard, RoleGuard],
    data: { role: 'ROLE_VIEW_DETAILS_PEDIDO' }
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PedidosRoutingModule { }
