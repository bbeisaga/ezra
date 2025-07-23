import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CabeceraComponent } from '../../../compartido/cabecera/cabecera.component';
import { PieComponent } from '../../../compartido/pie/pie.component';

@Component({
  selector: 'contactanos-cliente',
  standalone: true,
  templateUrl: './contactanos-cliente.component.html',
  styleUrl: './contactanos-cliente.component.css',
  imports:[RouterModule, CabeceraComponent, PieComponent]
})
export class ContactanosClienteComponent {

}
