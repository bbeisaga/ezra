import { Component, inject, OnInit } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { ItemService } from '../../../services/item.service';
import { Producto } from '../../../models/producto';
import { ChatUtils } from '../../../utils/chat-utils';
import { environment } from '../../../../environments/environment';
import { COLOR_ESTADO_PRODUCTO } from '../../../constants/color-estado-producto';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule, ActivatedRoute } from '@angular/router';




@Component({
  selector: 'app-productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css',
  standalone: true,
  imports: [CommonModule, MatDatepickerModule, MatTableModule, MatPaginatorModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

})
export class ProductosPorCategoriaComponent implements OnInit {


  private activatedRoute = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  private itemService = inject(ItemService);
  lstProductos: Producto[] = [];
  chatUtils = ChatUtils;
  constructor() {

  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      let categoriaId = + params.get('catId')!;
      this.productoService.productosPorCategoria(categoriaId).subscribe(resp => {
        this.lstProductos = resp.map(prd => {
          prd.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
          prd.imagen = environment.API_URL_VER_IMAGEN + prd.imagen;
          //console.log(prd.margenesProducto.map(margen => margen.precioNeto.toString()).toString());
          prd.precioNetoStringShow = "S/ ".concat(prd.margenesProducto.map(m => m.precioNeto).toString().replaceAll(',', ' - '));

          return prd;
        })


      })
    })

  }

  chatear(producto: Producto) {
    this.chatUtils.infoProduct(producto);
  }

}
