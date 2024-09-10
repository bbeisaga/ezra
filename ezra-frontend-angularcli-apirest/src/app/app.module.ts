import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { HeaderComponent } from './modules/compartido/components/header/header.component';
import { FooterComponent } from './modules/compartido/components/footer/footer.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { FormComponent } from './modules/clientes/components/form.component';
import { PaginatorComponent } from './modules/compartido/components/paginator/paginator.component';
import { ClienteService } from './services/cliente.service';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CommonModule, registerLocaleData } from '@angular/common';
import localeES from '@angular/common/locales/es';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatDatepickerModule } from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { DetalleComponent } from './modules/clientes/components/detalle.component';
/* import { LoginComponent } from './usuarios/login.component'; */
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { TokenInterceptor } from './modules/usuarios/interceptors/token.interceptor';
import { AuthInterceptor } from './modules/usuarios/interceptors/auth.interceptor';
import { DetallePedidoComponent } from './modules/pedidos/paginas/detalle-pedido.component';
import { PedidosComponent } from './modules/pedidos/paginas/pedidos.component';


import { MatAutocompleteModule } from '@angular/material/autocomplete';


// Angular Material
import { MatSliderModule } from '@angular/material/slider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatCardModule} from '@angular/material/card';
import { CompartidoModule } from './modules/compartido/compartido.module';
import { AppRoutingModule } from './app-routing.module';


registerLocaleData(localeES, 'es');

@NgModule({
  declarations: [
   /*  AppComponent,
    HeaderComponent,
    FooterComponent,
    DirectivaComponent,
    ClientesComponent,
    FormComponent,
    PaginatorComponent,
    DetalleComponent,
    LoginComponent,
    DetallePedidoComponent,
    PedidosComponent */

  ],
  imports: [
/*     BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule, MatDatepickerModule, MatMomentDateModule,
    ReactiveFormsModule, MatAutocompleteModule,
    CommonModule,
    MatSliderModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatTableModule,
    MatFormFieldModule, MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule, */
    AppRoutingModule,
    CompartidoModule
  ],

  schemas: [ CUSTOM_ELEMENTS_SCHEMA],

  providers: [
/*     ClienteService,
    { provide: LOCALE_ID, useValue: 'es' },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, */
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
