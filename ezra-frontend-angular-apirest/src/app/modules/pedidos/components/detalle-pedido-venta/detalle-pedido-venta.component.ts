import { Component, OnInit } from '@angular/core';
import { PedidoService } from '../../../../services/pedido.service';
import { ActivatedRoute } from '@angular/router';
import { Pedido } from '../../../../models/pedido';

@Component({
  selector: 'app-detalle-pedido-venta',
  templateUrl: './detalle-pedido-venta.component.html',
  styleUrl: './detalle-pedido-venta.component.css'

})
export class DetallePedidoVentaComponent implements OnInit {

  pedido!: Pedido;
  titulo: string = 'Pedido de venta';
  //razonSocialActivate:boolean=false;

  constructor(private pedidoService: PedidoService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      let pedidoId = +params.get('pedidoId')!;
      this.pedidoService.getPedido(pedidoId).subscribe(pedido => {
        this.pedido = pedido
        /*         if(this.pedido!.cliente!.razonSocial.length>0 ){
                  this.razonSocialActivate=true;
                } */
        console.log("Detalle pedido....", this.pedido)

      });
    });
  }

}
