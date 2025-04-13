import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovimientoComponent } from './pages/movimiento/movimiento.component';
import { MovimientoCajaComponent } from './pages/movimiento-caja/movimiento-caja.component';
import { RpteMovimientoEnCajaComponent } from './reportes/rpte-movimiento-en-caja/rpte-movimiento-en-caja.component';
import { RpteMovimientoEnCajaPorUsuarioComponent } from './reportes/rpte-movimiento-en-caja-por-usuario/rpte-movimiento-en-caja-por-usuario.component';

const routes: Routes = [
    { path: '', component: MovimientoComponent},
    { path: 'caja', component: MovimientoCajaComponent},
    { path: 'rpte-mov-en-caja', component: RpteMovimientoEnCajaComponent},
    { path: 'rpte-mov-en-caja-por-usuario', component: RpteMovimientoEnCajaPorUsuarioComponent},



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovimientosRoutingModule { }
