import { Component } from '@angular/core';
import { Categoria } from '../../../../models/categoria';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.component.html',
  styleUrl: './cabecera.component.css'
})
export class CabeceraComponent {

  constructor(private router: Router){

  }



  showProductsCategory(catagoria: Categoria){
    this.router.navigate(["/tienda/productos-categoria",catagoria.id])
  }

}
