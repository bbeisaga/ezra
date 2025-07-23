import { Component } from '@angular/core';
import { CabeceraComponent } from '../../../compartido/cabecera/cabecera.component';
import { PieComponent } from '../../../compartido/pie/pie.component';
import { TrabajadoresComponent } from "../../components/trabajadores/trabajadores.component";
import { EmpresaComponent } from '../../components/empresa/empresa.component';
import { ExperienciaComponent } from "../../components/experiencia/experiencia.component";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nosotros',
  standalone: true,
  templateUrl: './nosotros.component.html',
  styleUrl: './nosotros.component.css',
  imports: [TrabajadoresComponent, EmpresaComponent, ExperienciaComponent, RouterModule]
})
export class NosotrosComponent {

}
