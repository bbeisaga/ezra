import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuariosComponent } from './pages/usuarios.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';
import { AsignarRolUsuarioComponent } from './components/asignar-rol-usuario/asignar-rol-usuario.component';

const routes: Routes = [
    { path: '',
      component: UsuariosComponent
    },
    { path: 'asignar-rol-usuario',
      component: AsignarRolUsuarioComponent
    },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
