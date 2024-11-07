import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesRoutingModule } from './clientes-routing.module';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ClientesComponent } from './pages/clientes.component';
import { DetalleComponent } from './components/detalle.component';
import { FormComponent } from './components/form.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CompartidoModule } from '../compartido/compartido.module';



@NgModule({
  declarations: [
    ClientesComponent,
    FormComponent,
    DetalleComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    ClientesRoutingModule,
    FormsModule,
    CompartidoModule,
  ]
})
export class ClientesModule { }
