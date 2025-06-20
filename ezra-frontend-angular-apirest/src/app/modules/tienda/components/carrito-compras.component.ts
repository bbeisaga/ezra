import { Router } from '@angular/router';
import { ItemService } from './../../../services/item.service';
import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { ItemPedido } from '../../../models/item-pedido';
import { MatDialog } from '@angular/material/dialog';
import { DetalleItemPedidoComponent } from '../../pedidos/components/detalle-item-pedido/detalle-item-pedido.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'carrito-compras',
  templateUrl: './carrito-compras.component.html',
  styleUrl: './carrito-compras.component.css'
})
export class CarritoComprasComponent implements OnInit, OnDestroy {
  router = inject(Router);
  itemService = inject(ItemService)
  itemServiceSuscription$!: Subscription;
  //@Input() lstItemPedido: ItemPedido[] = [];
  lstItemPedido: ItemPedido[] = [];
  @Input() tipoPedido!: string;

  item!: ItemPedido;
  total: number = 0;

  constructor(
    public matDialog: MatDialog,
  ) {
    this.itemServiceSuscription$ = this.itemService.getItems().subscribe({
      next: items => {
        this.lstItemPedido = items;
        this.calcularTotal();
      },
      error: error => {
        console.error('Error al obtener el item:', error);
      }
    })
  }

  ngOnInit(): void {
    console.log("CarritoComprasComponent.ngOnInit", this.lstItemPedido);
    if (this.lstItemPedido.length === 0) {
      this.lstItemPedido = this.itemService.getLocalStorageItems()
      this.calcularTotal();
    }
    /*    this.lstItemPedido = [...this.lstItemPedido, {...this.itemPedido} ] */
  }

  actualizarCantidad(productoId: number, event: any): void {
    const cantidad: number = parseInt(event.target.value);
    this.lstItemPedido = this.itemService.UpdateAmountItemFromItems(this.lstItemPedido, productoId, cantidad);
    this.itemService.setItems(this.lstItemPedido);
    this.itemService.saveLocalStorageItems(this.lstItemPedido);
  }

  eliminarItemPedido(id: number): void {
    this.lstItemPedido = this.itemService.deleteItemFromItems(this.lstItemPedido, id);
    this.itemService.setItems(this.lstItemPedido);
    this.itemService.saveLocalStorageItems(this.lstItemPedido);
  }

  calcularTotal() {
    this.total = this.itemService.calculateTotalFromItems(this.lstItemPedido)
  }

  /*   openDialog(itemPedido: ItemPedido) {
      const dialogRef = this.matDialog.open(DetalleItemPedidoComponent, {
        data: itemPedido.descripcion,
        width: '550px',
        disableClose: true
      });
  
      dialogRef.afterClosed().subscribe(result => {
        itemPedido.descripcion = result
        console.log('Item Pedido: ', itemPedido);
        //this.animal = result;
      });
    } */

  irRealizarPedido() {
    this.router.navigate(['/tienda/pedido']) 
  }

  ngOnDestroy(): void {
    this.itemServiceSuscription$.unsubscribe();
  }

}
