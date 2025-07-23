import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenuTiendaComponent } from '../compartido/menus-nav/menu-tienda.component';

@Component({
  selector: 'app-tienda',
  standalone: true,
  templateUrl: './tienda.component.html',
  styleUrl: './tienda.component.css',
  imports:[RouterModule,MenuTiendaComponent]
})
export class TiendaComponent implements OnInit {

  constructor(private router: Router){}
  ngOnInit(): void {
    //navegara al boton TIENDA del menu
    this.router.navigate(["/productos/productos-categoria", 0])
  }

}
