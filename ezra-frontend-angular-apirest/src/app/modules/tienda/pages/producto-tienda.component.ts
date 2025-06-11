import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { Producto } from '../../../models/producto';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-producto-tienda',
  templateUrl: './producto-tienda.component.html',
  styleUrl: './producto-tienda.component.css'
})
export class ProductoTiendaComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute)
  private productoService = inject(ProductoService)
  private formBuilder = inject(FormBuilder)

  producto: Producto = new Producto;
  //formProductoTienda!: FormGroup;


  constructor() {

  }

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(params => {
      let productoId = + params.get('productoId')!;
      this.productoService.getProducto(productoId).subscribe(resp => {
        this.producto = resp;
        console.log(this.producto)
      })
    })

  }


}
