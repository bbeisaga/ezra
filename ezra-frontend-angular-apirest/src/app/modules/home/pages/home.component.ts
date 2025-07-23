import { CarruselProductosComponent } from '../components/carrusel-productos/carrusel-productos.component';
import { Component, Inject, OnInit } from '@angular/core';
import { LoadJavascriptService } from '../../../services/load-javascript.service';
import { CarruselServiciosComponent } from '../components/carrusel-servicios/carrusel-servicios.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CarruselEmpresaComponent } from "../components/carrusel-empresa/carrusel-empresa.component";
import { Categoria } from '../../../models/categoria';
import { ProductoService } from '../../../services/producto.service';
import { RouterModule } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { PieComponent } from "../../compartido/pie/pie.component";
import { CabeceraComponent } from "../../compartido/cabecera/cabecera.component";
import { TrabajadoresComponent } from "../components/trabajadores/trabajadores.component";
import { ExperienciaComponent } from '../components/experiencia/experiencia.component';
import { EmpresaComponent } from '../components/empresa/empresa.component';
import { ServiciosClienteComponent } from '../components/servicios-cliente/servicios-cliente.component';
import { ProductoClienteComponent } from '../components/producto-cliente/producto-cliente.component';
import { CategoriasProductoClienteComponent } from "../components/categorias-producto-cliente/categorias-producto-cliente.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports: [ServiciosClienteComponent, CommonModule, FormsModule, CarruselEmpresaComponent, RouterModule, PieComponent, CabeceraComponent, TrabajadoresComponent, ExperienciaComponent, EmpresaComponent, ProductoClienteComponent, CategoriasProductoClienteComponent]
})
export class HomeComponent {





  constructor() { }


  /*   constructor(private loadJavascriptService: LoadJavascriptService) {

       loadJavascriptService.load(['easing.min', 'waypoints.min', 'counterup.min', 'isotope.pkgd.min', 'owl.carousel.min', 'lightbox.min', 'main']);
    } */



}
