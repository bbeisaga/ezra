import { Pedido } from '../../../models/pedido';
import { PedidoService } from './../../../services/pedido.service';
import { Component, inject } from '@angular/core';

@Component({
  selector: 'app-pasarela-pago',
  templateUrl: './pasarela-pago.component.html',
  styleUrl: './pasarela-pago.component.css'
})
export class PasarelaPagoComponent {

  private pedidoService = inject(PedidoService);
  pedido! : Pedido;

  constructor() {
        this.pedido = {...this.pedidoService.pedido};
  } 




}
