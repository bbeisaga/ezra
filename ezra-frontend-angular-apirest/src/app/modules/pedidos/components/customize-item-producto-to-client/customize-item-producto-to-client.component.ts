import { Component, inject, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { MediosUtilsService } from '../../../../services/medios-utils.service';
import { FormBuilder } from '@angular/forms';
import { ProductoService } from '../../../../services/producto.service';
import { ItemService } from '../../../../services/item.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { FormUtils } from '../../../../utils/form-utils';
import { ChatUtils } from '../../../../utils/chat-utils';
import { Producto } from '../../../../models/producto';
import { ItemPedido } from '../../../../models/item-pedido';
import { Subscription } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { COLOR_ESTADO_PRODUCTO } from '../../../../constants/color-estado-producto';

@Component({
  selector: 'customize-item-producto-to-client',
  templateUrl: './customize-item-producto-to-client.component.html',
  styleUrl: './customize-item-producto-to-client.component.css'
})
export class CustomizeItemProductoToClientComponent implements OnInit, OnChanges {
  public mediosUtilsService = inject(MediosUtilsService);
  private formBuilder = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private itemService = inject(ItemService);
  private activatedRoute = inject(ActivatedRoute);
  public authService = inject(AuthService);

  @Input() productoId!: number;

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
    if (this.items.length === 0) {
      this.items = this.itemService.getLocalStorageItems()
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['productoId'].currentValue != changes['productoId'].previousValue) {
      this.productoService.getProducto(this.productoId).subscribe(prd => {
        this.producto = prd;
        this.producto.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
        this.gruposDe = this.producto.gruposDe
        this.minCantidadPedido = this.producto.minCantidadPedido
        this.maxCantidadPedido = this.producto.maxCantidadPedido
        this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
        this.frm.get('cantidad')?.setValue(this.minCantidadPedido);
      });

      this.verImagenItem = environment.API_URL_VER_IMAGEN + this.item.imagen;
    }
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }


  isImage(fileInput: HTMLInputElement): boolean {
    return this.mediosUtilsService.isImage(fileInput);
  }

  subirImagen(fileInput: HTMLInputElement) {
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
    this.item.producto = { ...this.producto };
    this.item.imagenUri = environment.API_URL_VER_IMAGEN + this.item.imagen
    if (this.itemService.existItemInItems(this.items, this.item.producto.id)) {
      this.items = this.itemService.UpdateAmountItemFromExterno(this.items, this.item.producto.id, this.item.cantidad);
    }
    else {
      this.items = [...this.items, { ...this.item }];
    }
    this.itemService.setItems(this.items);
    this.itemService.saveLocalStorageItems(this.items);
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
