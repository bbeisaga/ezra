import { ProductoService } from './../../../../services/producto.service';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { Tag } from 'primeng/tag';
import { Producto } from '../../../../models/producto';
import { COLOR_ESTADO_PRODUCTO } from '../../../../constants/color-estado-producto';
import { environment } from '../../../../../environments/environment';




@Component({
    selector: 'app-carrusel-servicios',
    templateUrl: './carrusel-servicios.component.html',
    styleUrl: './carrusel-servicios.component.css',
    standalone: true,
    imports: [ButtonModule, CarouselModule, FormsModule, CommonModule]

})
export class CarruselServiciosComponent {
    private productService = inject(ProductoService)

    lstProductos: Producto[] = [];

    responsiveOptions: any[] | undefined;

    constructor() { }

    ngOnInit() {
        this.productService.getAllProducto().subscribe(resp => {
            this.lstProductos = resp.map(prd => {
                prd.estadoProducto.color = COLOR_ESTADO_PRODUCTO[('' + prd.estadoProducto.id) as keyof typeof COLOR_ESTADO_PRODUCTO];
                prd.imagen = environment.API_URL_VER_IMAGEN + prd.imagen;
                //console.log(prd.margenesProducto.map(margen => margen.precioNeto.toString()).toString());
                prd.precioNetoStringShow = "S/ ".concat(prd.margenesProducto.map(m => m.precioNeto).toString().replaceAll(',', ' - '));

                return prd;
            })
        });


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

    /*     getSeverity(status: string) {
            switch (status) {
                case 'INSTOCK':
                    return 'success';
                case 'LOWSTOCK':
                    return 'warn';
                case 'OUTOFSTOCK':
                    return 'danger';
            
            }
        } */
}
