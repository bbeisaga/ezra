import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalComponent } from './modules/principal/paginas/principal/principal.component';
import { AyudaComponent } from './modules/principal/paginas/ayuda/ayuda.component';
import { ErrorComponent } from './modules/principal/paginas/error/error.component';
import { PrincipalModule } from './modules/principal/principal.module';
import { LoginComponent } from './modules/auth/login.component';
import { DashboardComponent } from './modules/home/pages/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { PaginaTiendaComponent } from './modules/tienda/pages/pagina-tienda.component';
import { CrearCuentaTiendaComponent } from './modules/tienda/pages/crear-cuenta-tienda/crear-cuenta-tienda.component';
import { ItemProductoTiendaComponent } from './modules/tienda/pages/item-producto/item-producto-tienda.component';
import { ProductosPorCategoriaComponent } from './modules/tienda/pages/productos-por-categoria/productos-por-categoria.component';
import { PedidoTiendaComponent } from './modules/tienda/pages/pedido-tienda/pedido-tienda.component';
import { PasarelaPagoComponent } from './modules/tienda/components/pasarela-pago.component';

const routes: Routes = [
  { path: '', redirectTo: 'tienda', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'crear-cuenta', component: CrearCuentaTiendaComponent },
  {
    path: 'tienda', component: PaginaTiendaComponent,
    children: [
      { path: 'productos-categoria/:catId', component: ProductosPorCategoriaComponent },
      { path: 'item-producto/:productoId', component: ItemProductoTiendaComponent },
      { path: 'pedido', component: PedidoTiendaComponent },
      { path: 'pasarela-pago', component: PasarelaPagoComponent }
    ]
  },
  {
    path: 'principal', component: PrincipalComponent,
    children: [
      {
        path: '',
        loadChildren: () => import("./modules/home/home.module").then((m) => m.HomeModule),
        canActivate: [AuthGuard],
      },
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
  { path: 'ayuda', component: AyudaComponent },
  { path: '***', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
