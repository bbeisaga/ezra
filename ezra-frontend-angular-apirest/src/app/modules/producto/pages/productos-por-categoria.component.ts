import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { ItemService } from '../../../services/item.service';
import { Producto } from '../../../models/producto';
import { ChatUtils } from '../../../utils/chat-utils';
import { environment } from '../../../../environments/environment';
import { COLOR_ESTADO_PRODUCTO } from '../../../constants/color-estado-producto';




@Component({
  selector: 'app-productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css'
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
      let categoriaId = + params.get('catId')!;
      this.productoService.productosPorCategoria(categoriaId).subscribe(resp => {
        this.lstProductos = resp.map(prd => {
          prd.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
          prd.imagen = environment.API_URL_VER_IMAGEN + prd.imagen;
          return prd;
        })
        console.log(this.lstProductos);
      })
    })


  }

  chatear(producto: Producto) {
    this.chatUtils.infoProduct(producto);
  }

}
