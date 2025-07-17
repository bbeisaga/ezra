import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './modules/auth/login.component';

import { PrincipalComponent } from './modules/compartido/principal.component';
import { CrearCuentaTiendaComponent } from './modules/auth/crear-cuenta-tienda.component';
import { HomeComponent } from './modules/home/pages/home/home.component';
import { CarruselServiciosComponent } from './modules/home/components/carrusel-servicios/carrusel-servicios.component';

const routes: Routes = [

  /*    { path: '', redirectTo: 'a', pathMatch: 'full' },
   */
  { path: 'login', component: LoginComponent },
  { path: 'crear-cuenta', component: CrearCuentaTiendaComponent },

   { path: '', component: HomeComponent },
 

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
    path: 'tienda', component: PrincipalComponent,
    children: [
      /*       {
              path: 'tienda',
              loadChildren: () => import("./modules/tienda/tienda.module").then((m) => m.TiendaModule),
            }, */
      /*       {
              path: '',
              loadChildren: () => import("./modules/home/home.module").then((m) => m.HomeModule),
              canActivate: [AuthGuard],
            }, */
      {
        path: 'clientes',
        loadChildren: () => import("./modules/clientes/clientes.module").then((m) => m.ClientesModule),
      },
      {
        path: 'pedidos',
        loadChildren: () => import("./modules/pedidos/pedidos.module").then((m) => m.PedidosModule),
      },
      {
        path: 'cajas',
        loadChildren: () => import("./modules/caja/caja.module").then((m) => m.CajaModule),
      },
      {
        path: 'movimientos',
        loadChildren: () => import("./modules/movimientos/movimientos.module").then((m) => m.MovimientosModule),
      },
      {
        path: 'productos',
        loadChildren: () => import("./modules/producto/producto.module").then((m) => m.ProductoModule),
      },
      {
        path: 'usuarios',
        loadChildren: () => import("./modules/usuarios/usuarios.module").then((m) => m.UsuariosModule),
      }

    ]
  },
  //{ path: 'pr',  loadChildren: () =>import("./modules/principal/principal.module").then((m) => m.PrincipalModule),},
  //{ path: 'reset-password', component: AyudaComponent },
/*   { path: 'ayuda', component: AyudaComponent },
 */  { path: '***', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
