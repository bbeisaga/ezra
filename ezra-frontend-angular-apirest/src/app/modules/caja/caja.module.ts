import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CajaRoutingModule } from './caja-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { IngresoEgresoCajaComponent } from './pages/ingreso-egreso-caja/ingreso-egreso-caja.component';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja/apertura-cierre-caja.component';


@NgModule({
  declarations: [
    IngresoEgresoCajaComponent,
    AperturaCierreCajaComponent
  ],
  imports: [
    CommonModule,
    CajaRoutingModule,
    CompartidoModule
  ]
})
export class CajaModule { }
