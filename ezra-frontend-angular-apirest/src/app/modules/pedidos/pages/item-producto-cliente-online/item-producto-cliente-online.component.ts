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
export class ItemProductoClienteOnlineComponent implements OnInit, OnDestroy {
  public mediosUtilsService = inject(MediosUtilsService);
  private formBuilder = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private itemService = inject(ItemService);
  private activatedRoute = inject(ActivatedRoute);
  public authService = inject(AuthService);

  itemServiceSuscription$!: Subscription;
  formUtils = FormUtils;
  chatUtils = ChatUtils;
  verImagenProducto!: string;
  verImagenItem!: string;
  producto!: Producto;
  item = new ItemPedido()
  items: ItemPedido[] = [];
  gruposDe!: number;
  minCantidadPedido!: number;
  maxCantidadPedido!: number;
  frm = this.formBuilder.group({
    descripcion: [''],
    cantidad: [0],
  })
  constructor() {
    this.itemService.getItems().subscribe({
      next: items => {
        this.items = items;
      },
      error: error => {
        console.error('Error al obtener el item:', error);
      }
    })
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const productoId = + params.get('productoId')!;
      this.productoService.getProducto(productoId).subscribe(prd => {
        this.producto = prd;
        this.gruposDe = this.producto.gruposDe
        this.minCantidadPedido = this.producto.minCantidadPedido
        this.maxCantidadPedido = this.producto.maxCantidadPedido
        this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
        this.frm.get('cantidad')?.setValue(this.minCantidadPedido);
      })
    })

    if (this.items.length === 0) {
      this.items = this.itemService.getLocalStorageItems()
    }

    this.verImagenItem = environment.API_URL_VER_IMAGEN + this.item.imagen;

  }
  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  /*   seleccionarImagenToItem($event: any): void {
      this.mediosUtilsService.seleccionarImagen($event);
    } */

  isImage(fileInput: HTMLInputElement): boolean {
    //console.log('isImage', fileInput.value); 
    return this.mediosUtilsService.isImage(fileInput);
  }

  subirImagen(fileInput: HTMLInputElement) {
    //if (this.mediosUtilsService.imagenSeleccionada) {
    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      const imagen: File = fileInput.files[0];
      this.mediosUtilsService.subirImagen(imagen).subscribe(resp => {
        //this.mediosUtilsService.imageToBase64(imagen);
        this.verImagenItem = environment.API_URL_VER_IMAGEN + resp.imagen;
        this.item.imagen = resp.imagen;
        // this.mediosUtilsService.imagenSeleccionada = null;  Limpiar la imagen seleccionada
      })
    }
    // }
  }


  sendOneItemProducto() {
    this.item.cantidad = (this.frm.get('cantidad')?.value ? this.frm.get('cantidad')?.value : 0)!;
    this.item.importe = this.item.cantidad * this.producto.precioNeto;
    this.item.descripcion = (this.frm.get('descripcion')?.value ? this.frm.get('descripcion')?.value : '')!;
    //this.item.imagen = (this.frm.get('imagen')?.value ? this.frm.get('imagen')?.value : 'no-imagen.jpg')!;
    this.item.producto = { ...this.producto };
    if (this.itemService.existItemInItems(this.items, this.item.producto.id)) {
      this.items = this.itemService.UpdateAmountItemFromExterno(this.items, this.item.producto.id, this.item.cantidad);
    }
    else {
      //this.itemService.addAmountItemFromItems()
      this.items = [...this.items, { ...this.item }];
    }
    this.itemService.setItems(this.items);
    this.itemService.saveLocalStorageItems(this.items);
    //this.oneItemProductoEmit.emit(this.item);
  }

  chatear(producto: Producto) {
    this.chatUtils.infoProduct(producto);
  }

  ngOnDestroy(): void {
    if (this.itemServiceSuscription$) {
      this.itemServiceSuscription$.unsubscribe();
    }
  }


}