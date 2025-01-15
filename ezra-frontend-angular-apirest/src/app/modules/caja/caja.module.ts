import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CajaRoutingModule } from './caja-routing.module';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja.component';
import { CompartidoModule } from '../compartido/compartido.module';


@NgModule({
  declarations: [
    AperturaCierreCajaComponent
  ],
  imports: [
    CommonModule,
    CajaRoutingModule,
    CompartidoModule
  ]
})
export class CajaModule { }
