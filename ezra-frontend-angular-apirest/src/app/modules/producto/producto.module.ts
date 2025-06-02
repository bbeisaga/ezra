import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductoRoutingModule } from './producto-routing.module';
import { CompartidoModule } from '../compartido/compartido.module';
import { ProductoComponent } from './pages/producto.component';
import { FormProductoComponent } from './components/form-producto.component';
import { MantenimientoProductoComponent } from './pages/mantenimiento-producto.component';


@NgModule({
  declarations: [ProductoComponent,FormProductoComponent, MantenimientoProductoComponent],
  imports: [
    CommonModule,
    ProductoRoutingModule,
    CompartidoModule
  ]
})
export class ProductoModule { }
