import { CarruselProductosComponent } from './modules/home/components/carrusel-productos/carrusel-productos.component';
import { LoginComponent } from './modules/auth/login.component';

import { Routes } from '@angular/router';
import { CrearCuentaTiendaComponent } from './modules/auth/crear-cuenta-tienda.component';
import { PrincipalComponent } from './modules/compartido/principal.component';
import { HomeComponent } from './modules/home/pages/home/home.component';
import { CarruselEmpresaComponent } from './modules/home/components/carrusel-empresa/carrusel-empresa.component';

export const routes: Routes = [

  /*    { path: '', redirectTo: 'a', pathMatch: 'full' },
   */
  { path: 'login', component: LoginComponent },
  { path: 'crear-cuenta', component: CrearCuentaTiendaComponent },

   { path: 'home', component: HomeComponent},


  /*   {
      path: 'tienda', component: PaginaTiendaComponent,
      children: [
        { path: 'productos-categoria/:catId', component: ProductosPorCategoriaComponent },
        { path: 'item-producto/:productoId', component: ItemProductoTiendaComponent },
        { path: 'pedido', component: PedidoTiendaComponent },
        { path: 'pasarela-pago', component: PasarelaPagoComponent }
      ]
    }, */
  {
    path: '', component: PrincipalComponent,
    children: [
      /*       {
              path: 'tienda',
              loadChildren: () => import("./modules/tienda/tienda.module").then((m) => m.TiendaModule),
            }, */
      /*       {
              path: '',
              loadChildren: () => import("./modules/home/home.module").then((m) => m.HomeModule),
              canActivate: [isAuthenticatedGuard],
            }, */
      {
        path: 'clientes',
        loadChildren: () => import("./modules/clientes/clientes.routes").then((r) => r.routes ),
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
  //{ path: 'pr',  loadChildren: () =>import("./modules/principal/principal.module").then((m) => m.PrincipalModule),},
  //{ path: 'reset-password', component: AyudaComponent },
/*   { path: 'ayuda', component: AyudaComponent },
 */  { path: '***', component: LoginComponent },
];

/* @NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } */
