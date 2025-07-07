import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovimientoComponent } from './pages/movimiento/movimiento.component';
import { MovimientoCajaComponent } from './pages/movimiento-caja/movimiento-caja.component';
import { RpteMovimientoEnCajaComponent } from './reportes/rpte-movimiento-en-caja/rpte-movimiento-en-caja.component';
import { RpteMovimientoEnCajaPorUsuarioComponent } from './reportes/rpte-movimiento-en-caja-por-usuario/rpte-movimiento-en-caja-por-usuario.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';

const routes: Routes = [
    { path: '',
      component: MovimientoComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_REGISTER_PAGO_PEDIDO'}
    },
    { path: 'caja',
      component: MovimientoCajaComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_REGISTER_MOVCAJA'}
    },
    { path: 'rpte-mov-en-caja',
      component: RpteMovimientoEnCajaComponent ,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_REPORT_MOVCAJA'}
    },
    { path: 'rpte-mov-en-caja-por-usuario',
      component: RpteMovimientoEnCajaPorUsuarioComponent ,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_RPTE_MOVCAJA_USUARIO'}
    },



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovimientosRoutingModule { }
