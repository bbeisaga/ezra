import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientesComponent } from './pages/clientes.component';
import { FormComponent } from './components/form.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';



const routes: Routes = [

/*   { path: '', redirectTo: 'clientes', pathMatch: 'full' }, */
  { path: '',
    component: ClientesComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_LIST_CLIENTES' }
  },
  { path: 'form/:id', component: FormComponent, 
    canActivate: [AuthGuard, RoleGuard], 
    data: { role: 'ROLE_UPDATE_CLIENTE' }  },
  { path: 'form', component: FormComponent, 
    canActivate: [AuthGuard, RoleGuard], 
    data: { role: 'ROLE_REGISTER_CLIENTE' } },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesRoutingModule { }
