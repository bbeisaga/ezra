import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CajaRoutingModule } from './caja-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { IngresoEgresoCajaComponent } from './pages/ingreso-egreso-caja/ingreso-egreso-caja.component';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja/apertura-cierre-caja.component';
import { RpteCajaComponent } from './reportes/rpte-cierre-caja/rpte-caja.component';
import { RpteCajaPorUsuarioComponent } from './reportes/rpte-caja-por-usuario/rpte-caja-por-usuario.component';


@NgModule({
  declarations: [
    IngresoEgresoCajaComponent,
    AperturaCierreCajaComponent,
    RpteCajaComponent,
    RpteCajaPorUsuarioComponent
  ],
  imports: [
    CommonModule,
    CajaRoutingModule,
    CompartidoModule
  ]
})
export class CajaModule { }
