import { Routes } from '@angular/router';
import { isAuthenticatedGuard } from '../../guards/is-authenticated.guard';
import { RoleGuard } from '../../guards/role.guard';
import { MantenimientoProductoComponent } from './pages/mantenimiento-producto.component';
import { ProductoComponent } from './pages/producto.component';
import { ProductosPorCategoriaComponent } from './pages/productos-por-categoria.component';

export const routes: Routes = [
  {
    path: '',
    component: ProductoComponent,
    canActivate: [isAuthenticatedGuard, RoleGuard],
    data: { role: 'ROLE_LIST_PRODUCTOS' },
    pathMatch: 'full'
  },
  /*   { path: 'form-producto', component: FormProductoComponent,
      canActivate:[isAuthenticatedGuard,RoleGuard],
      data: { role: 'ROLE_REGISTER_PRODUCTO' }
     }, */

  {
    path: 'mantenimiento-producto', component: MantenimientoProductoComponent,
    canActivate: [isAuthenticatedGuard, RoleGuard],
    data: { role: 'ROLE_REGISTER_PRODUCTO' }
  },

  {
    path: 'mantenimiento-producto/:productoId', component: MantenimientoProductoComponent,
    canActivate: [isAuthenticatedGuard, RoleGuard],
    data: { role: 'ROLE_UPDATE_PRODUCTO' }
  },

  /*   { path: 'form-producto/:productoId', component: FormProductoComponent,
      canActivate:[isAuthenticatedGuard,RoleGuard],
      data: { role: 'ROLE_UPDATE_PRODUCTO' }
    }, */

  {
    path: 'productos-categoria/:catId',
    component: ProductosPorCategoriaComponent,
/*     canActivate: [isAuthenticatedGuard, RoleGuard],
    data: { role: 'ROLE_LIST_CLIENTES' } */
  }

];

/* @NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { } */
