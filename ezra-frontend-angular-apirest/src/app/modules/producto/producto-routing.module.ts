import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoComponent } from './pages/producto.component';
import { FormProductoComponent } from './components/form-producto.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';

const routes: Routes = [
  { path: '',
    component: ProductoComponent ,
    canActivate:[AuthGuard,RoleGuard],
    data: { role: 'ROLE_LIST_PRODUCTOS' },
    pathMatch: 'full'
  },
  { path: 'form-producto', component: FormProductoComponent,
    canActivate:[AuthGuard,RoleGuard],
    data: { role: 'ROLE_REGISTER_PRODUCTO' }
   },
  { path: 'form-producto/:productoId', component: FormProductoComponent,
    canActivate:[AuthGuard,RoleGuard],
    data: { role: 'ROLE_UPDATE_PRODUCTO' }
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
