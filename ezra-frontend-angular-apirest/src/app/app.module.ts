import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { CompartidoModule } from './modules/compartido/compartido.module';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { PrincipalModule } from './modules/principal/principal.module';
import { LoginComponent } from './modules/auth/login.component';
import { CommonModule, DatePipe, registerLocaleData } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ZonaHorariaDefectoService } from './services/zona-horaria-defecto.service';
import localeEsPE from '@angular/common/locales/es-PE';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { PaginaTiendaComponent } from './modules/tienda/pages/pagina-tienda.component';
import { CrearCuentaTiendaComponent } from './modules/tienda/pages/crear-cuenta-tienda/crear-cuenta-tienda.component';
import { LayoutModule, MediaMatcher } from '@angular/cdk/layout';
import { ItemProductoTiendaComponent } from './modules/tienda/pages/item-producto/item-producto-tienda.component';
import { CarritoComprasComponent } from './modules/tienda/components/carrito-compras.component';
import { PedidoTiendaComponent } from './modules/tienda/pages/pedido-tienda/pedido-tienda.component';
import { ProductosPorCategoriaComponent } from './modules/tienda/pages/productos-por-categoria/productos-por-categoria.component';
import { PasarelaPagoComponent } from './modules/tienda/components/pasarela-pago.component';


registerLocaleData(localeEsPE);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PaginaTiendaComponent,
    ProductosPorCategoriaComponent,
    CrearCuentaTiendaComponent,
    ItemProductoTiendaComponent,
    CarritoComprasComponent,
    PedidoTiendaComponent,
    PasarelaPagoComponent
    
    
  ],
  imports: [
    CommonModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    CompartidoModule,
    HttpClientModule,
    PrincipalModule,
    LayoutModule
  ],
  providers: [ZonaHorariaDefectoService,
    { provide: LOCALE_ID, useValue: 'es-PE' },
    provideAnimationsAsync(),
    DatePipe,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    MediaMatcher
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
