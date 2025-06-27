import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoComponent } from './pages/producto.component';
import { FormProductoComponent } from './components/form-producto.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';
import { MantenimientoProductoComponent } from './pages/mantenimiento-producto.component';
import { ProductosPorCategoriaComponent } from './pages/productos-por-categoria.component';

const routes: Routes = [
  {
    path: '',
    component: ProductoComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_PRODUCTOS' },
    pathMatch: 'full'
  },
  /*   { path: 'form-producto', component: FormProductoComponent,
      canActivate:[AuthGuard,RoleGuard],
      data: { role: 'ROLE_REGISTER_PRODUCTO' }
     }, */

  {
    path: 'mantenimiento-producto', component: MantenimientoProductoComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_REGISTER_PRODUCTO' }
  },

  {
    path: 'mantenimiento-producto/:productoId', component: MantenimientoProductoComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_UPDATE_PRODUCTO' }
  },

  /*   { path: 'form-producto/:productoId', component: FormProductoComponent,
      canActivate:[AuthGuard,RoleGuard],
      data: { role: 'ROLE_UPDATE_PRODUCTO' }
    }, */

  {
    path: 'productos-categoria/:catId',
    component: ProductosPorCategoriaComponent,
/*     canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_CLIENTES' } */
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
