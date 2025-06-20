import { AuthService } from './../../../../services/auth.service';
import { ItemPedido } from './../../../../models/item-pedido';
import { Component, inject, OnInit } from '@angular/core';
import { ClienteService } from '../../../../services/cliente.service';
import { Cliente } from '../../../../models/cliente';
import { TipoDocumento } from '../../../../models/tipo-documento';
import { findIndex } from 'lodash';
import { ItemService } from '../../../../services/item.service';
import { Subscription } from 'rxjs';
import { Usuario } from '../../../../models/usuario';
import { UsuarioService } from '../../../../services/usuario.service';
import { Pedido } from '../../../../models/pedido';
import { NgForm } from '@angular/forms';
import { PedidoService } from '../../../../services/pedido.service';
import { TipoPedido } from '../../../../models/tipo-pedido';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pedido-tienda',
  templateUrl: './pedido-tienda.component.html',
  styleUrl: './pedido-tienda.component.css'
})
export class PedidoTiendaComponent implements OnInit {


  private authService = inject(AuthService);
  private usuarioService = inject(UsuarioService);
  private clienteService = inject(ClienteService);
  private pedidoService = inject(PedidoService);
    private router = inject(Router);


  itemService = inject(ItemService)
  itemServiceSuscription$!: Subscription;
  cliente!: Cliente;
  pedido = new Pedido();
  //cliente: Cliente | null = null;
  tipoDocumentos: TipoDocumento[] = [];
  tipoDocumentoSelected!: TipoDocumento;
  tipoPedidoVentaClientes!: TipoPedido;
  tipoPedidos: TipoPedido[] = [];
  //usuario!: Usuario;

  lstItemPedido: ItemPedido[] = [];
  //itemPedido!: ItemPedido;
  total: number = 0;


  constructor() {
    this.itemServiceSuscription$ = this.itemService.getItems().subscribe({
      next: items => {
        this.lstItemPedido = items;
        console.log("this.lstItemPedido", this.lstItemPedido)
        this.calcularTotal();
      },
      error: error => {
        console.error('Error al obtener el item:', error);
      }
    })
  }

  ngOnInit(): void {
    this.clienteService.getTipoDocumento().subscribe(doc => {
      this.tipoDocumentos = doc
    });

    this.usuarioService.getUsuarioByUsername(this.authService.usuario.username).subscribe(usuario => {
      this.clienteService.getCliente(usuario.id).subscribe(cli => {
        this.cliente = cli;
        const index = this.findIndexDocument(this.cliente.tipoDocumento.id);
        this.tipoDocumentoSelected = this.tipoDocumentos[index];
        //this.pedido.items = this.lstItemPedido;
        this.pedido.cliente = this.cliente;
      });
    });


    if (this.lstItemPedido.length === 0) {
      this.lstItemPedido = this.itemService.getLocalStorageItems();
      this.calcularTotal();
    }

    this.pedidoService.getAllTipoPedido().subscribe(result => {
      this.tipoPedidos = result
      this.tipoPedidos.forEach(r =>
        r.activo = r.id == 1 ? true : false
      )
      this.tipoPedidoVentaClientes = this.tipoPedidos[0];
    });
  }

  findIndexDocument(tipoDocumentoId: number): number {
    return findIndex(this.tipoDocumentos, (td) => td.id == tipoDocumentoId)
  }

  calcularTotal() {
    this.total = this.itemService.calculateTotalFromItems(this.lstItemPedido)
  }

  eliminarItemPedido(id: number): void {
    this.lstItemPedido = this.itemService.deleteItemFromItems(this.lstItemPedido, id);
    this.itemService.setItems(this.lstItemPedido);
    this.itemService.saveLocalStorageItems(this.lstItemPedido);
  }


  actualizarCantidad(productoId: number, event: any): void {
    const cantidad: number = parseInt(event.target.value);
    this.lstItemPedido = this.itemService.UpdateAmountItemFromItems(this.lstItemPedido, productoId, cantidad);
    this.itemService.setItems(this.lstItemPedido);
    this.itemService.saveLocalStorageItems(this.lstItemPedido);
  }

  crearPedidoTienda(pedidoTiendaForm: NgForm) {
    if (this.lstItemPedido.length > 0) {
      this.pedido.items = [...this.lstItemPedido];
      this.calcularTotal();
      this.pedido.precioNetoTotal = this.total
    } else {
      //this.autocompleteControl.setErrors({ 'invalid': true });
      return
    }

    if (pedidoTiendaForm.form.valid && this.pedido.items.length > 0) {

      this.pedido.tipoPedido = this.tipoPedidoVentaClientes
      console.log(JSON.stringify(this.pedido));

      this.pedidoService.create(this.pedido).subscribe(p => {
        //this.alertService.success(`Pedido registrado, ahora REGISTRAR MOVIMIENTO!`,'success')
        //this.alertService.success(`Pedido para ${p.cliente?.nomApellRz}, creado con éxito!`,'success')
        this.pedidoService.setPedido(p);
        this.router.navigate(['/pasarela-pago']);
      });
    }
  }
}

