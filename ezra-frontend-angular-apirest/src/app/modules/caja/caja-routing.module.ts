import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja/apertura-cierre-caja.component';
import { RpteCajaComponent } from './reportes/rpte-cierre-caja/rpte-caja.component';
import { RpteCajaPorUsuarioComponent } from './reportes/rpte-caja-por-usuario/rpte-caja-por-usuario.component';

const routes: Routes = [
    { path: '',
      component: AperturaCierreCajaComponent,
     // canActivate: [AuthGuard, RoleGuard],
     // data: { role: 'ROLE_ADMIN' },
      pathMatch: 'full' },
    { path: 'rpte-caja',
      component: RpteCajaComponent },
    { path: 'rpte-caja-por-usuario',
      component: RpteCajaPorUsuarioComponent },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CajaRoutingModule { }
