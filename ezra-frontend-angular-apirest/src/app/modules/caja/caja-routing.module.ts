import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AperturaCierreCajaComponent } from './pages/apertura-cierre-caja.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';

const routes: Routes = [
    { path: '', component: AperturaCierreCajaComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' }, pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CajaRoutingModule { }
