import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovimientosRoutingModule } from './movimientos-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { MovimientoComponent } from './pages/movimiento/movimiento.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MovimientoCajaComponent } from './pages/movimiento-caja/movimiento-caja.component';


@NgModule({
  declarations: [
    MovimientoComponent,
    MovimientoCajaComponent],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MovimientosRoutingModule,
    CompartidoModule
  ]
})
export class MovimientosModule { }
