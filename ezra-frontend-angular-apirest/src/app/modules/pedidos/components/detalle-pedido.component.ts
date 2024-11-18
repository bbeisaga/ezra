import { Component, OnInit } from '@angular/core';
import { PedidoService } from '../../../services/pedido.service';
import { ActivatedRoute } from '@angular/router';
import { Pedido } from '../../../models/pedido';

@Component({
  selector: 'app-detalle-pedido',
  templateUrl: './detalle-pedido.component.html'
})
export class DetallePedidoComponent implements OnInit {

  pedido!: Pedido;
  titulo: string = 'Pedido';

  constructor(private pedidoService: PedidoService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      let pedidoId = +params.get('pedidoId')!;
      this.pedidoService.getPedido(pedidoId).subscribe(pedido => {
        this.pedido = pedido
        console.log("Detalle pedido....", this.pedido)

      });
    });
  }

}
