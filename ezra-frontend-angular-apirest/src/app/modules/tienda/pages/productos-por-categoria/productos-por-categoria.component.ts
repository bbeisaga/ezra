import { ChatUtils } from './../../../../utils/chat-utils';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Producto } from '../../../../models/producto';
import { ProductoService } from '../../../../services/producto.service';
import { environment } from '../../../../../environments/environment';
import { ItemPedido } from '../../../../models/item-pedido';
import { ItemService } from '../../../../services/item.service';



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

/*   sendOneProducto(producto: Producto) {
    this.item.cantidad = producto.minCantidadPedido;
    this.item.importe = producto.minCantidadPedido * producto.precioNeto;
    this.item.producto = { ...producto };
    if (this.itemService.existItemInItems(this.items, this.item.producto.id)) {
      this.items = this.itemService.UpdateAmountItemFromExterno(this.items, this.item.producto.id, this.item.cantidad);
    }
    else {
      this.items = [...this.items, { ...this.item }];
    }
    this.itemService.setItems(this.items);
    this.itemService.saveSessionStorageItems(this.items);
  } */

}
