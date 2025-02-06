import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovimientoVentaComponent } from './pages/movimiento-venta/movimiento-venta.component';
import { MovimientoCajaComponent } from './pages/movimiento-caja/movimiento-caja.component';

const routes: Routes = [
    { path: 'venta', component: MovimientoVentaComponent},
    { path: 'caja', component: MovimientoCajaComponent},


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovimientosRoutingModule { }
