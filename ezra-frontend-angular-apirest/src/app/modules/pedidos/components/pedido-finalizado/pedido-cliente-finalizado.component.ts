import { AuthService } from '../../../../services/auth.service';
import { ItemPedido } from '../../../../models/item-pedido';
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
import { ActivatedRoute, Router } from '@angular/router';
import { FormUtils } from '../../../../utils/form-utils';
import moment from 'moment';
import { ChatUtils } from '../../../../utils/chat-utils';

@Component({
  selector: 'pedido-cliente-finalizado',
  templateUrl: './pedido-cliente-finalizado.component.html',
  styleUrl: './pedido-cliente-finalizado.component.css'
})
export class PedidoClienteFinalizadoComponent implements OnInit {


  public authService = inject(AuthService);
  private activatedRoute = inject(ActivatedRoute);
  private clienteService = inject(ClienteService);
  private pedidoService = inject(PedidoService);
  private router = inject(Router);


  itemService = inject(ItemService)
  itemServiceSuscription$!: Subscription;
  cliente!: Cliente;
  pedido = new Pedido();
  tipoDocumentos: TipoDocumento[] = [];
  tipoDocumentoSelected!: TipoDocumento;
  tipoPedidoVentaClientes!: TipoPedido;
  tipoPedidos: TipoPedido[] = [];

  lstItemPedido: ItemPedido[] = [];
  formUtils = FormUtils;
  chatUtils = ChatUtils;
  total: number = 0;
  clienteOnline!: boolean;


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


    this.activatedRoute.paramMap.subscribe(params => {
      let clienteId = +params.get('clienteId')!;
      this.clienteService.getCliente(clienteId).subscribe(cli => {
        const now = new Date();
        this.cliente = cli;
        const index = this.findIndexDocument(this.cliente.tipoDocumento.id);
        this.tipoDocumentoSelected = this.tipoDocumentos[index];
        this.pedido.cliente = this.cliente;
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        this.pedido.entregadoEn = moment(now).add(2, 'days').toISOString().slice(0, 16);
      });
    });

    this.activatedRoute.queryParams.subscribe(params => {
      const value = params['clienteOnline'];
      this.clienteOnline = value ? value.toLocaleLowerCase() === 'true' : false;
    })

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

  actualizarImporte(productoId: number, event: any): void {
    const precio: number = parseFloat(event.target.value);
    this.lstItemPedido = this.itemService.UpdatePrecioItemFromItemsCliete(this.lstItemPedido, productoId, precio);
    this.calcularTotal();

    this.itemService.setItems(this.lstItemPedido);
    this.itemService.saveLocalStorageItems(this.lstItemPedido);
  }

  crearPedidoTienda(pedidoTiendaForm: NgForm) {
    this.pedido.items = [...this.lstItemPedido];
    if (pedidoTiendaForm.form.valid && this.pedido.items.length > 0) {
      this.calcularTotal();
      this.pedido.precioNetoTotal = this.total
      this.pedido.tipoPedido = this.tipoPedidoVentaClientes
      this.pedidoService.createPedidoTienda(this.pedido).subscribe(p => {
        this.pedidoService.setPedido(p);
        this.itemService.removeLocalStorageItems();
        if (this.clienteOnline) {
          this.router.navigate(['/pedidos/contactanos']);
        } else {
          this.router.navigate(['/movimientos']);
        }
      });
    }
  }


}

