import { Component, OnInit } from '@angular/core';
import { PedidoService } from '../../../../services/pedido.service';
import { Pedido } from '../../../../models/pedido';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalle-pedido-venta',
  templateUrl: './detalle-pedido-venta.component.html',
  styleUrl: './detalle-pedido-venta.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

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
