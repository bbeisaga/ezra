import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-productos-por-categoria',
  templateUrl: './productos-por-categoria.component.html',
  styleUrl: './productos-por-categoria.component.css'
})
export class ProductosPorCategoriaComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute){
  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params=> {
      let categoriaId = params.get('catId');
      console.log("ProductosPorCategoriaComponent.ngOnInit categoriaId=", categoriaId );
    })
  }

}
