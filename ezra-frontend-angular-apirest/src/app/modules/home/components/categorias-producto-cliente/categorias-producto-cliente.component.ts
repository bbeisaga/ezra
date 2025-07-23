import { Component } from '@angular/core';
import { Categoria } from '../../../../models/categoria';
import { ProductoService } from '../../../../services/producto.service';
import { environment } from '../../../../../environments/environment';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'categorias-producto-cliente',
  standalone: true,
  templateUrl: './categorias-producto-cliente.component.html',
  styleUrl: './categorias-producto-cliente.component.css',
  imports:[RouterModule]
})
export class CategoriasProductoClienteComponent {

  lstCategoria: Categoria[] = []


  constructor(private productoService: ProductoService) { }

  ngOnInit(): void {
    this.productoService.getCategoriasActivas().subscribe(categorias => {
      this.lstCategoria = categorias.map(cat => {
        cat.imagen = environment.API_URL_VER_IMAGEN + cat.imagen;
        return cat;
      }).sort((a, b) => a.orden - b.orden)
      console.log("this.lstCategoria", this.lstCategoria);
    })

  }

}
