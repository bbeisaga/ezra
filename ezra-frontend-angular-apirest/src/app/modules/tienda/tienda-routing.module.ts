import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductosPorCategoriaComponent } from './pages/productos-por-categoria/productos-por-categoria.component';
import { ItemProductoTiendaComponent } from './pages/item-producto/item-producto-tienda.component';
import { PedidoTiendaComponent } from './pages/pedido-tienda/pedido-tienda.component';
import { PasarelaPagoComponent } from './components/pasarela-pago.component';

const routes: Routes = [
  {
    path: 'productos-categoria/:catId',
    component: ProductosPorCategoriaComponent,
    // canActivate: [AuthGuard, RoleGuard],
    // data: { role: 'ROLE_LIST_CLIENTES' }
  },
  {
    path: 'item-producto/:productoId', component: ItemProductoTiendaComponent,
    // canActivate: [AuthGuard, RoleGuard], 
    // data: { role: 'ROLE_UPDATE_CLIENTE' }  
  },
  {
    path: 'pedido', component: PedidoTiendaComponent,
    // canActivate: [AuthGuard, RoleGuard], 
    // data: { role: 'ROLE_REGISTER_CLIENTE' }
  },

  {
    path: 'pasarela-pago', component: PasarelaPagoComponent,
    // canActivate: [AuthGuard, RoleGuard], 
    // data: { role: 'ROLE_REGISTER_CLIENTE' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiendaRoutingModule { }
