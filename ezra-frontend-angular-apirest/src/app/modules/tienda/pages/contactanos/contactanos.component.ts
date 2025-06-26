import { Pedido } from '../../../../models/pedido';
import { PedidoService } from '../../../../services/pedido.service';
import { Component, inject } from '@angular/core';
import { ChatUtils } from '../../../../utils/chat-utils';

@Component({
  selector: 'contactanos',
  templateUrl: './contactanos.component.html',
  styleUrl: './contactanos.component.css'
})
export class ContactanosComponent {

  private pedidoService = inject(PedidoService);
  pedido = new Pedido();
  chatUtils = ChatUtils;

  constructor() {
        this.pedido = {...this.pedidoService.pedido};
        console.log(this.pedido);
  } 

 enviarPedidoChat() {
    this.chatUtils.sendPedido(this.pedido);
  } 

}
