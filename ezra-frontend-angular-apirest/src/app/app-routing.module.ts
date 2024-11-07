import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalComponent } from './modules/principal/paginas/principal/principal.component';
import { AyudaComponent } from './modules/principal/paginas/ayuda/ayuda.component';
import { ErrorComponent } from './modules/principal/paginas/error/error.component';
import { PrincipalModule } from './modules/principal/principal.module';
import { LoginComponent } from './modules/auth/login.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'pr', component: PrincipalComponent ,
    children:[
      { path: '', redirectTo: 'clientes', pathMatch: 'full' },
      { path: 'clientes', loadChildren: () =>import("./modules/clientes/clientes.module").then((m) => m.ClientesModule), },
  /*     { path: 'clientes/form', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
      { path: 'clientes/form/:id', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } }, */
    ]



  },
  //{ path: 'pr',  loadChildren: () =>import("./modules/principal/principal.module").then((m) => m.PrincipalModule),},
  //{ path: 'reset-password', component: AyudaComponent },
  { path: 'ayuda', component: AyudaComponent },
  { path: '***', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
