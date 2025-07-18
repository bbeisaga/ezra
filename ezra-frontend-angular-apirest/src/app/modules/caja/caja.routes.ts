import { Routes } from '@angular/router';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja/apertura-cierre-caja.component';
import { RpteCajaPorUsuarioComponent } from './reportes/rpte-caja-por-usuario/rpte-caja-por-usuario.component';
import { RpteCajaComponent } from './reportes/rpte-cierre-caja/rpte-caja.component';

export const routes: Routes = [
    { path: '',
      component: AperturaCierreCajaComponent,
      canActivate: [AuthGuard],
      pathMatch: 'full' },
    { path: 'rpte-caja',
      component: RpteCajaComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_REPORT_CJU' }
    },
    { path: 'rpte-caja-por-usuario',
      component: RpteCajaPorUsuarioComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_REPORT_USUARIO_CJU' }
    },


];

/* @NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CajaRoutingModule { } */
