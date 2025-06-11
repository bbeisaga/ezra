import { Component, EventEmitter, OnInit, output, Output, Renderer2 } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { Categoria } from '../../../models/categoria';
import { Producto } from '../../../models/producto';

@Component({
  selector: 'app-menu-principal-tienda',
  templateUrl: './menu-principal-tienda.component.html',
  styleUrl: './menu-principal-tienda.component.css'
})
export class MenuPrincipalTiendaComponent {


  lstCategoria: Categoria[] = []
  categoria!: Categoria;
  //@Output() categoriaClickEventEmit: EventEmitter<Categoria> = new EventEmitter();
  //@Output() categoriaIdClickEventEmit: EventEmitter<number> = new EventEmitter();
  //categoriaIdEmit = output<number>()



  constructor(private productoService: ProductoService,
              private renderer2: Renderer2
  ) {

  }

  ngOnInit(): void {
    //this.productoService.getAllProducto().subscribe(resp => this.lstProductos = resp);

    this.productoService.getCategoriasProducto().subscribe(resp => this.lstCategoria = resp.sort((a, b) => a.orden - b.orden))



  }

/*   setLinkActive(event: any) {
    console.log(event.target);
    const element = event.target;//me trae el elemento nativo
    this.renderer2.setAttribute(element,'aria-current','page'); //adiciona propiedades
    this.renderer2.setAttribute(element,'class',"nav-link active");
    console.log(event.target);

  } */

  //activateCategory(categoriaId: number){
    //this.categoriaClickEventEmit.emit(categoria);
    //this.categoriaIdClickEventEmit.emit(categoria.id);
    //this.categoriaIdEmit.emit(categoriaId)
  //}

}
