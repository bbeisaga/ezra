import { Routes } from '@angular/router';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';
import { AsignarRolUsuarioComponent } from './components/asignar-rol-usuario/asignar-rol-usuario.component';
import { UsuariosComponent } from './pages/usuarios.component';

export const routes: Routes = [
    { path: '',
      component: UsuariosComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_LIST_USUARIOS' }
    },
    { path: 'asignar-rol-usuario',
      component: AsignarRolUsuarioComponent,
      canActivate:[AuthGuard, RoleGuard],
      data: { role: 'ROLE_ASIGNAR_ROL_USUARIO' }
    },

];

/* @NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { } */
