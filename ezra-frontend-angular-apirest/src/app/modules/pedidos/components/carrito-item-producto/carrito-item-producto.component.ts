import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../../services/auth.service';
import { ItemService } from '../../../../services/item.service';
import { ClienteService } from '../../../../services/cliente.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { ItemPedido } from '../../../../models/item-pedido';
import { environment } from '../../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule, Router } from '@angular/router';


@Component({
  selector: 'carrito-item-producto',
  templateUrl: './carrito-item-producto.component.html',
  styleUrl: './carrito-item-producto.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule]

})
export class CarritoItemProductoComponent implements OnInit, OnDestroy {
  router = inject(Router);
  public authService = inject(AuthService);
  itemService = inject(ItemService)
  clienteService = inject(ClienteService);
  usuarioService = inject(UsuarioService)


  itemServiceSuscription$!: Subscription;
  //@Input() lstItemPedido: ItemPedido[] = [];
  lstItemPedido: ItemPedido[] = [];
  @Input() tipoPedido!: string;
  @Input() clienteId!: number;
  //@Input() clienteOnline!: boolean;
  item!: ItemPedido;
  total: number = 0;

  constructor(
    public matDialog: MatDialog,
  ) {
    this.itemServiceSuscription$ = this.itemService.getItems().subscribe({
      next: items => {
        //this.lstItemPedido = items;
        this.lstItemPedido = this.itemService.importePorMargenCantidad(items);
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
      this.lstItemPedido = this.itemService.importePorMargenCantidad(this.itemService.getLocalStorageItems());
      this.calcularTotal();
    }
    /*     this.lstItemPedido.map(item => {
          item.imagen = environment.API_URL_VER_IMAGEN + item.imagen ;
          return item;
        }) */
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
    if (this.authService.isAuthenticated()) {
      if (this.clienteId == 0) {
        this.usuarioService.getUsuarioByUsername(this.authService.usuario.username)
          .subscribe(usr => {
            this.clienteService.getClienteByUsuarioId(usr.id).subscribe(cli => {
              this.router.navigate(['/pedidos/pedido-cliente-finalizado', cli.id], { queryParams: { clienteOnline: 'true' } })
            })
          });
      } else {
        this.router.navigate(['/pedidos/pedido-cliente-finalizado', this.clienteId], { queryParams: { clienteOnline: 'false' } })
      }
    }
  }

  ngOnDestroy(): void {
    this.itemServiceSuscription$.unsubscribe();
  }

}
