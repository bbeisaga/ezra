import { Component, EventEmitter, OnInit, Output, Renderer2 } from '@angular/core';
import { ProductoService } from '../../../../services/producto.service';
import { Categoria } from '../../../../models/categoria';
import { Producto } from '../../../../models/producto';

@Component({
  selector: 'app-menu-horizontal',
  templateUrl: './menu-horizontal.component.html',
  styleUrl: './menu-horizontal.component.css'
})
export class MenuHorizontalComponent {


  lstCategoria: Categoria[] = []
  categoria!: Categoria;
  @Output() categoriaClickEventEmit: EventEmitter<Categoria> = new EventEmitter();


  constructor(private productoService: ProductoService,
              private renderer2: Renderer2
  ) {

  }

  ngOnInit(): void {
    //this.productoService.getAllProducto().subscribe(resp => this.lstProductos = resp);

    this.productoService.getCategoriasProducto().subscribe(resp => this.lstCategoria = resp.sort((a, b) => a.orden - b.orden))



  }

  setLinkActive(event: any) {
    console.log(event.target);
    const element = event.target;//me trae el elemento nativo
    this.renderer2.setAttribute(element,'aria-current','page'); //adiciona propiedades
    this.renderer2.setAttribute(element,'class',"nav-link active");
    console.log(event.target);

  }

  activateCategory(categoria: Categoria){
    this.categoriaClickEventEmit.emit(categoria);
  }

}
