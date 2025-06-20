import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompartidoModule } from '../compartido/compartido.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { PrincipalComponent } from './paginas/principal/principal.component';
import { AyudaComponent } from './paginas/ayuda/ayuda.component';
import { ErrorComponent } from './paginas/error/error.component';

/*    import { LayoutModule, MediaMatcher } from '@angular/cdk/layout';
 */

@NgModule({
  declarations: [
    PrincipalComponent,
    AyudaComponent,
    ErrorComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    CompartidoModule,
    

  ],
  exports: [
    PrincipalComponent
  ]
})
export class PrincipalModule { }
