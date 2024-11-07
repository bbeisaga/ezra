import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalComponent } from './paginas/principal/principal.component';
import { ClientesComponent } from '../clientes/pages/clientes.component';
import { FormComponent } from '../clientes/components/form.component';
import { AuthGuard } from '../../guards/auth.guard';
import { RoleGuard } from '../../guards/role.guard';

const routes: Routes = [

/* { path: '', component: PrincipalComponent,
  children:[
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', loadChildren: () =>import("../../modules/clientes/clientes.module").then((m) => m.ClientesModule), },
     { path: 'clientes/form', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
    { path: 'clientes/form/:id', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
  ]
}, */

/* { path: 'page/:page', component: ClientesComponent },
{ path: 'form/:id', component: FormComponent },
  { path: 'clientes/form', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
{ path: 'clientes/form/:id', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } }, */

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrincipalRoutingModule { }
