import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../../../services/alert.service';
import { AuthService } from '../../../services/auth.service';
import { Producto } from '../../../models/producto';
import { FormBuilder, FormGroup } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { Color } from '../../../models/color';
import { Material } from '../../../models/material';
import { Categoria } from '../../../models/categoria';
import { Uso } from '../../../models/uso';


@Component({
  selector: 'app-mantenimiento-producto',
  templateUrl: './mantenimiento-producto.component.html',
  styleUrl: './mantenimiento-producto.component.css'
})
export class MantenimientoProductoComponent implements OnInit, AfterViewInit {

  producto: Producto = new Producto();
  formProducto!: FormGroup;
  colores: Color[] = [];
  materiales: Material[] = [];
  categorias: Categoria[] = [];
  usos: Uso[] = [];


  private imagenSeleccionada!: File;

  constructor(private productoService: ProductoService,
    private router: Router,
    private alertService: AlertService,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) { }



  ngOnInit(): void {
    this.createForm();

    //console.log("ngAfterViewInit",this.genericosDeProducto);
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
        console.log("producto.sinnada", this.producto);

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

  ngAfterViewInit(): void {
    this.cargarDatosAuxiliares();

  }

  createForm(): void {

    // console.log("cajaUsuariocreateForm", this.cajaUsuario);
    this.formProducto = this.formBuilder.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      nombre: [this.producto?.nombre],
      descripcion: [this.producto?.descripcion],
      unbralPocaCantidad: [this.producto?.unbralPocaCantidad],
      unbralAgotadaCantidad: [this.producto?.unbralAgotadaCantidad],
      cantidadStock: [this.producto?.cantidadStock],
      minCantidadPedido: [this.producto?.minCantidadPedido],
      maxCantidadPedido: [this.producto?.maxCantidadPedido],
      gruposDe: [this.producto?.gruposDe],
      costoUnitario: [this.producto?.costoUnitario],
      costoUnitarioEmpaque: [this.producto?.costoUnitarioEmpaque],
      precioBruto: [this.producto?.precioBruto],
      precioBrutoRebajado: [this.producto?.precioBrutoRebajado],
      precioNeto: [this.producto?.precioNeto],
      precioNetoRabajado: [this.producto?.precioNetoRabajado],
      fechaPrecioRebajadoDesde:[this.producto?.fechaPrecioRebajadoDesde],
      fechaPrecioRebajadoHasta:[this.producto?.fechaPrecioRebajadoHasta],

      categoria:[this.producto?.categoria],

      /*
            saldoCaja: [this.cajaUsuario?.saldoCaja, {
              validators: [Validators.required]
            }],
            saldoPorConteo: [this.cajaUsuario?.saldoPorConteo, {
              validators: [Validators.required]
            }],

            fchApertura: [this.cajaUsuario?.fechaApertura],
            fchCierre: [this.cajaUsuario?.fechaCierre],
            cajaPorAsignar: [this.cajaUsuario?.caja?.id, {
              validators: [Validators.required]
            }], */
    })
  }

  crearProducto() {
    this.producto.nombre = this.formProducto.get('nombre')?.value;
    this.producto.descripcion = this.formProducto.get('descripcion')?.value;
    if (this.imagenSeleccionada) {
      let formData = new FormData();
      formData.append("archivo", this.imagenSeleccionada);
      formData.append("producto", JSON.stringify(this.producto));
      this.productoService.createProductoImagen(formData).subscribe(
        resp => {
          this.alertService.success('Porducto con imagen principal ha sido creado exitosamente', 'Producto');
          this.router.navigate(['/productos']);
        },
        err => {
          err.error.err as string[];
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        })
    } else {
      this.productoService.createProducto(this.producto).subscribe(resp => {
        this.alertService.success('Porducto ha sido creado exitosamente', 'Producto');
        this.router.navigate(['/productos']);

      })
    }
  }

  seleccionarImagen(event: any) {
    this.imagenSeleccionada = event.target.files[0];
    //this.progreso = 0;
    //console.log(this.fotoSeleccionada);
    if (this.imagenSeleccionada!.type.indexOf('image') < 0) {
      this.alertService.error('El archivo debe ser del tipo imagen', 'Imagen');
      //   this.fotoSeleccionada = null;
    }
  }

  cargarDatosAuxiliares(): void {
    this.productoService.getColoresProducto().subscribe(resp => this.colores = resp);
    this.productoService.getMaterialesProducto().subscribe(resp => this.materiales = resp);
    this.productoService.getCategoriasProducto().subscribe(resp => this.categorias = resp);
    this.productoService.getUsosInternoProducto().subscribe(resp => this.usos = resp);
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

}
