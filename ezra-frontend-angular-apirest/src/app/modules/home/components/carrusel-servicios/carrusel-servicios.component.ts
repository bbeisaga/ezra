import { ProductoService } from './../../../../services/producto.service';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { Tag } from 'primeng/tag';
import { Producto } from '../../../../models/producto';




@Component({
  selector: 'app-carrusel-servicios',
  templateUrl: './carrusel-servicios.component.html',
  styleUrl: './carrusel-servicios.component.css',
  standalone: true,
  imports: [ButtonModule, CarouselModule, FormsModule, CommonModule, Tag]

})
export class CarruselServiciosComponent {
  private productService= inject(ProductoService)

 products: Producto[] = [];

    responsiveOptions: any[] | undefined;

    constructor() {}

    ngOnInit() {
      this.productService.getAllProducto().subscribe(pr => this.products = pr);


        this.responsiveOptions = [
            {
                breakpoint: '1400px',
                numVisible: 2,
                numScroll: 1,
            },
            {
                breakpoint: '1199px',
                numVisible: 3,
                numScroll: 1,
            },
            {
                breakpoint: '767px',
                numVisible: 2,
                numScroll: 1,
            },
            {
                breakpoint: '575px',
                numVisible: 1,
                numScroll: 1,
            },
        ];
    }

    getSeverity(status: string) {
        switch (status) {
            case 'INSTOCK':
                return 'success';
            case 'LOWSTOCK':
                return 'warn';
            case 'OUTOFSTOCK':
                return 'danger';
        }
    }
}
