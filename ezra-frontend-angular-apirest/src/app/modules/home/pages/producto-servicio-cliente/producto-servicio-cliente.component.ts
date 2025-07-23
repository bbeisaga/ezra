import { CabeceraComponent } from './../../../compartido/cabecera/cabecera.component';
import { Component } from '@angular/core';
import { ProductoClienteComponent } from '../../components/producto-cliente/producto-cliente.component';
import { CategoriasProductoClienteComponent } from '../../components/categorias-producto-cliente/categorias-producto-cliente.component';
import { ServiciosClienteComponent } from '../../components/servicios-cliente/servicios-cliente.component';
import { PieComponent } from '../../../compartido/pie/pie.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-producto-servicio-cliente',
  standalone: true,
  templateUrl: './producto-servicio-cliente.component.html',
  styleUrl: './producto-servicio-cliente.component.css',
  imports:[CabeceraComponent, ProductoClienteComponent, CategoriasProductoClienteComponent, ServiciosClienteComponent, PieComponent, RouterModule]
})
export class ProductoServicioClienteComponent {

}
