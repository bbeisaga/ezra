import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovimientoComponent } from './pages/movimiento/movimiento.component';
import { MovimientoCajaComponent } from './pages/movimiento-caja/movimiento-caja.component';

const routes: Routes = [
    { path: '', component: MovimientoComponent},
    { path: 'caja', component: MovimientoCajaComponent},


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovimientosRoutingModule { }
