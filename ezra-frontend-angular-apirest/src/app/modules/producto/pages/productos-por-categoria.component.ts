import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { COLOR_ESTADO_PRODUCTO } from '../../../constants/color-estado-producto';
import { Producto } from '../../../models/producto';
import { ItemService } from '../../../services/item.service';
import { ProductoService } from '../../../services/producto.service';
import { ChatUtils } from '../../../utils/chat-utils';
import { AngularMaterialModule } from '../../compartido/angular-material.module';




@Component({
  selector: 'app-productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, AngularMaterialModule ]

})
export class ProductosPorCategoriaComponent implements OnInit {


  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  private itemService = inject(ItemService);
  lstProductos: Producto[] = [];
  chatUtils = ChatUtils;
  constructor() {

  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      let categoriaId = + params.get('categoriaId')!;
      this.productoService.productosPorCategoria(categoriaId).subscribe(resp => {
        this.lstProductos = resp.map(prd => {
          prd.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
          prd.imagen = environment.API_URL_VER_IMAGEN + prd.imagen;
          //console.log(prd.margenesProducto.map(margen => margen.precioNeto.toString()).toString());
          prd.precioNetoStringShow = "S/ ".concat(prd.margenesProducto.map(m => m.precioNeto).toString().replaceAll(',', ' - '));

          return prd;
        })


      })
    })

  }

  chatear(producto: Producto) {
    this.chatUtils.infoProduct(producto);
  }

}
