import { Component, OnInit } from '@angular/core';
import { PedidoService } from './services/pedido.service';
import { Pedido } from './models/pedido';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalle-pedido',
  templateUrl: './detalle-pedido.component.html'
})
export class DetallePedidoComponent implements OnInit {

  pedido: Pedido;
  titulo: string = 'Pedido';

  constructor(private pedidoService: PedidoService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('id');
      this.pedidoService.getPedido(id).subscribe(pedido => this.pedido = pedido);
    });
  }

}
