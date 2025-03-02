import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Cliente } from '../../../models/cliente';
import { Region } from '../../../models/region';
import { ClienteService } from '../../../services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { TipoDocumento } from '../../../models/tipo-documento';
import { Producto } from '../../../models/producto';
import { ProductoService } from '../../../services/producto.service';
import { GenericosDeProducto } from '../../../models/genericos-de-producto';
import { GenericosDeProductoService } from '../../../services/genericos-de-producto.service';
import { Color } from '../../../models/color';
import { Material } from '../../../models/material';
import { Origen } from '../../../models/origen';
import { Empaque } from '../../../models/empaque';
import { Categoria } from '../../../models/categoria';
import { AlertService } from '../../../services/alert.service';
import { find } from 'lodash';
import { Uso } from '../../../models/uso';

@Component({
  selector: 'app-form-producto',
  templateUrl: './form-producto.component.html'
})
export class FormProductoComponent implements OnInit, AfterViewInit {

  producto: Producto = new Producto();
  colores:Color[]=[];
  materiales: Material[]=[];
  //patronRegexpMedidas = "[0-9]{3}([X])[0-9]{3}*";
  //origenes: Origen[]=[];
  //empaques: Empaque[]=[];
  categorias: Categoria[]=[];
  usos: Uso[]=[];
  //tipoDocumentos: TipoDocumento[]=[];
  titulo: string = "Crear Producto";
  //genericosDeProducto = new GenericosDeProducto();
  genericosDeProducto:GenericosDeProducto[]=[];
  errores: string[]=[];

  constructor(private productoService: ProductoService,
    private genericosDeProductoService: GenericosDeProductoService,
    private router: Router,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.cargarDatosAuxiliares();

  }

  ngAfterViewInit(): void {
    console.log("ngAfterViewInit",this.genericosDeProducto);
    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('productoId')!;
      if (id) {
        this.productoService.getProducto(id).subscribe(resp => {
          this.producto = resp
        //  this.producto.categoria = Object.assign({},this.findObjectInGenericos(this.producto.categoriaId))
        //  this.producto.uso = Object.assign({}, this.findObjectInGenericos(this.producto.usoId!));

        });
        //console.log("producto=>", this.producto);
      } else {
       // console.log("producto.sinnada", this.producto);

      }
    });


/*     let colores =  this.producto.genericos.filter(p => p.id>=10 && p.id<30);
    let materiales = this.producto.genericos.filter(p => p.id>=30 && p.id<50);
    let origenes = this.producto.genericos.filter(p => p.id>=50 && p.id<70);
    let empaques = this.producto.genericos.filter(p => p.id>=70 && p.id<90);
    let categorias = this.producto.genericos.filter(p => p.id>=90 && p.id<100);
    let usos = this.producto.genericos.filter(p => p.id>=100 && p.id<120);
    this.colores = [...colores];
    this.materiales=[...materiales]
    this.origenes=[...origenes]
    this.empaques=[...empaques]
    this.categorias=[...categorias]
    this.usos=[...usos] */

    //console.log("categorias",this.categorias);
  }

  cargarDatosAuxiliares():void{
    this.productoService.getColoresProducto().subscribe(resp => this.colores = resp);
    this.productoService.getMaterialesProducto().subscribe(resp=> this.materiales = resp);
    this.productoService.getCategoriasProducto().subscribe(resp => this.categorias=resp);
    this.productoService.getUsosInternoProducto().subscribe(resp => this.usos=resp);
    //this.genericosDeProductoService.getGenericos().subscribe(result =>{
    //  this.genericosDeProducto = result
    //  this.colores =  result.filter(p => p.id>=10 && p.id<30);
    //  this.materiales = result.filter(p => p.id>=30 && p.id<50);
    //  this.origenes = result.filter(p => p.id>=50 && p.id<70);
    //  this.empaques = result.filter(p => p.id>=70 && p.id<90);
    //  this.categorias = result.filter(p => p.id>=90 && p.id<100);
    //  this.usos = result.filter(p => p.id>=100 && p.id<120);
    //});
  }


  findObjectInGenericos( id:number ){
    console.log("findObjectInGenericos", this.genericosDeProducto);
    return find(this.genericosDeProducto, {"id": id})
  }

  create(): void {
    console.log(this.producto);
    this.producto.peso = this.producto.peso?.toUpperCase();
    this.producto.medidas = this.producto.medidas?.toUpperCase();
    this.productoService.createProducto(this.producto).subscribe(
        producto => {
         // swal.fire('Nuevo cliente', `El cliente ${cliente.nombres} ha sido creado con éxito`, 'success');
         this.alertService.success(`${producto.nombre} ha sido creado con éxito`,'Nueo producto');
         this.router.navigate(['/pr/productos']);

        },
        err => {
          this.errores = err.error.errors as string[];
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        }
      );
  }

  update(): void {
    //this.cliente.pedidos = [];
    console.log(this.producto);
    this.producto.peso = this.producto.peso?.toUpperCase();
    this.producto.medidas = this.producto.medidas?.toUpperCase();
    this.productoService.updateProducto(this.producto).subscribe(
        json => {
          //swal.fire('Cliente Actualizado', `${json.mensaje}: ${json.producto.nombre}`, 'success');
          this.alertService.success(`${json.mensaje}: ${json.producto.nombre}`, 'Producto actualizado');
          this.router.navigate(['/pr/productos']);

        },
        err => {
          this.errores = err.error.errors as string[];
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        }
      )
  }

  compareValueWithOptions(value: any, option: any): boolean {
    if (value === undefined && option === undefined) {
      return true;
    }
    return value === null || option === null || value === undefined || option === undefined ? false : value.id === option.id;
  }

}
