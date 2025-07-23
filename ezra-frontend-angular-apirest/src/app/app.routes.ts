import { LoginComponent } from './modules/auth/login.component';
import { TiendaComponent } from './modules/tienda/tienda.component';

import { Routes } from '@angular/router';
import { CrearCuentaTiendaComponent } from './modules/auth/crear-cuenta-tienda.component';
import { PrincipalComponent } from './modules/compartido/principal.component';

export const routes: Routes = [

  /*    { path: '', redirectTo: 'a', pathMatch: 'full' },
   */
  { path: 'login', component: LoginComponent },
  { path: 'crear-cuenta', component: CrearCuentaTiendaComponent },
  {
    path: '', component: PrincipalComponent,
    children: [
      {
        path: 'home',
        loadChildren: () => import("./modules/home/home.routes").then((r) => r.routes),
      },
      {
        path: '', component: TiendaComponent,
        children: [
          {
            path: 'clientes',
            loadChildren: () => import("./modules/clientes/clientes.routes").then((r) => r.routes),
          },
          {
            path: 'pedidos',
            loadChildren: () => import("./modules/pedidos/pedidos.routes").then((r) => r.routes),
          },
          {
            path: 'cajas',
            loadChildren: () => import("./modules/caja/caja.routes").then((r) => r.routes),
          },
          {
            path: 'movimientos',
            loadChildren: () => import("./modules/movimientos/movimientos.routes").then((r) => r.routes),
          },
          {
            path: 'productos',
            loadChildren: () => import("./modules/producto/producto.routes").then((r) => r.routes),
          },
          {
            path: 'usuarios',
            loadChildren: () => import("./modules/usuarios/usuarios.routes").then((r) => r.routes),
          }

        ]

      },
    ]
  },
  { path: '***', component: PrincipalComponent },
];


