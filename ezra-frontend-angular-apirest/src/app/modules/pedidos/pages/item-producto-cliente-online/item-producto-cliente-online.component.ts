import { ItemService } from '../../../../services/item.service';
import { FormUtils } from '../../../../utils/form-utils';


import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediosUtilsService } from '../../../../services/medios-utils.service';
import { Producto } from '../../../../models/producto';
import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { ItemPedido } from '../../../../models/item-pedido';
import { ProductoService } from '../../../../services/producto.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { AuthService } from '../../../../services/auth.service';
import { ChatUtils } from '../../../../utils/chat-utils';

@Component({
  selector: 'item-producto-cliente-online',
  templateUrl: './item-producto-cliente-online.component.html',
  styleUrl: './item-producto-cliente-online.component.css'
})
export class ItemProductoClienteOnlineComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  public authService = inject(AuthService);
  producto!: Producto;
  constructor() { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const productoId = + params.get('productoId')!;
      this.productoService.getProducto(productoId).subscribe(prd => {
        this.producto = prd;

      })
    })
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }


}