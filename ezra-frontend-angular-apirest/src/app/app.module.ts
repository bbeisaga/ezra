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
import { AuthInterceptor } from './modules/clientes/interceptors/auth.interceptor';
import { TokenInterceptor } from './modules/clientes/interceptors/token.interceptor';
import { ZonaHorariaDefectoService } from './services/zona-horaria-defecto.service';
import  localeEsPE  from '@angular/common/locales/es-PE';

registerLocaleData(localeEsPE);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    CompartidoModule,
    HttpClientModule,
    PrincipalModule,
  ],
  providers: [ZonaHorariaDefectoService,
    {provide: LOCALE_ID, useValue: 'es-PE'},
    provideAnimationsAsync(),
    DatePipe
/*     { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },*/
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
