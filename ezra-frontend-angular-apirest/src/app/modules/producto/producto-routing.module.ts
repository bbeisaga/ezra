import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoComponent } from './pages/producto.component';
import { FormProductoComponent } from './components/form-producto.component';

const routes: Routes = [
  { path: '', component: ProductoComponent , pathMatch: 'full'},
  { path: 'form-producto', component: FormProductoComponent },
  { path: 'form-producto/:productoId', component: FormProductoComponent },
  //{ path: ':pedidoId', component: DetallePedidoComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
