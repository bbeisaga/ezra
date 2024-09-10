import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrincipalComponent } from './modules/compartido/paginas/principal/principal.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'principal', pathMatch: 'full' },
  { path: 'principal', component: PrincipalComponent },

  /*   { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'directivas', component: DirectivaComponent },
    { path: 'clientes', component: ClientesComponent },
    { path: 'clientes/page/:page', component: ClientesComponent },
    { path: 'clientes/form', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
    { path: 'clientes/form/:id', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
     { path: 'login', component: LoginComponent },
    { path: 'pedidos/:id', component: DetallePedidoComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_USER' } },
    { path: 'pedidos/form/:clienteId', component: PedidosComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } } */
  ];

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule]
})
export class AppRoutingModule { }
