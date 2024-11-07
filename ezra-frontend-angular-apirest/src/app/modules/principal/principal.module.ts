import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrincipalRoutingModule } from './principal-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { PrincipalComponent } from './paginas/principal/principal.component';
import { AyudaComponent } from './paginas/ayuda/ayuda.component';
import { ErrorComponent } from './paginas/error/error.component';



@NgModule({
  declarations: [
    PrincipalComponent,
    AyudaComponent,
    ErrorComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    PrincipalRoutingModule,
    CompartidoModule

  ],
  exports: [
    PrincipalComponent
  ]
})
export class PrincipalModule { }
