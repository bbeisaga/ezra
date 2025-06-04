import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../../../services/alert.service';
import { AuthService } from '../../../services/auth.service';
import { Producto } from '../../../models/producto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { Color } from '../../../models/color';
import { Material } from '../../../models/material';
import { Categoria } from '../../../models/categoria';
import { Uso } from '../../../models/uso';
import { find } from 'lodash';


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

/*   categoriaId!: number;
  materialId!: number;
  colorId!: number;
  usoId!: number; */
  titulo: string = "Crear Producto";



  private imagenSeleccionada!: File;

  constructor(private productoService: ProductoService,
    private router: Router,
    private alertService: AlertService,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) { }



  ngOnInit(): void {
    this.createForm();
    this.cargarDatosAuxiliares();

    //console.log("ngAfterViewInit",this.genericosDeProducto);
    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('productoId')!;
      if (id) {
        this.productoService.getProducto(id).subscribe(resp => {
          this.producto = resp
          this.createForm();

          console.log("aaaaaaa", this.producto);
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

  }

  createForm(): void {
    console.log("productos1", this.producto);

    this.formProducto = this.formBuilder.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      nombre: [this.producto?.nombre, Validators.required],
      codigo: [this.producto?.codigo,
      //Pateon letras, números y guion . Al menos un número o letra en la cadena.
      { validators: [Validators.required, Validators.minLength(2), Validators.pattern('^[A-Z0-9-]*[A-Z0-9][A-Z0-9-]*$')] }
      ],
      descripcion: [this.producto?.descripcion, Validators.required],
      medidas: [this.producto?.medidas,
      { validators: [Validators.minLength(2), Validators.pattern('^[0-9]{1,3}([\\.][0-9]{2})?(x|X)?([0-9]{1,3}([\\.][0-9]{2})?)?(x|X)?([0-9]{1,3}([\\.][0-9]{2})?)?\\s(cm|CM|mt|MT)$')] }
      ],
      peso: [this.producto?.peso,
      { validators: [Validators.minLength(4), Validators.pattern('^[0-9]{1,3}([(\\.)][0-9]{2})?\\s(kg|KG)$')] }
      ],
      unbralPocaCantidad: [this.producto?.unbralPocaCantidad, Validators.min(1)],
      unbralAgotadaCantidad: [this.producto?.unbralAgotadaCantidad, Validators.min(0)],
      cantidadStock: [
        { value: this.producto?.cantidadStock, disabled: true },
        { validators: [Validators.required, Validators.min(0)] }
      ],
      minCantidadPedido: [this.producto?.minCantidadPedido, Validators.min(1)],
      maxCantidadPedido: [this.producto?.maxCantidadPedido, Validators.min(100000)],
      gruposDe: [this.producto?.gruposDe, Validators.min(1)],
      costoUnitario: [
        { value: this.producto?.costoUnitario, disabled: true },
        { validators: [Validators.required, Validators.min(1)] }
      ],
      costoUnitarioEmpaque: [this.producto?.costoUnitarioEmpaque, Validators.min(0)],
      precioBruto: [this.producto?.precioBruto, Validators.min(3)],
      precioNeto: [this.producto?.precioNeto, Validators.min(4)],
      precioBrutoRebajado: [this.producto?.precioBrutoRebajado, Validators.min(2)],
      precioNetoRabajado: [this.producto?.precioNetoRabajado, Validators.min(3)],
      fechaPrecioRebajadoDesde: [this.producto?.fechaPrecioRebajadoDesde],
      fechaPrecioRebajadoHasta: [this.producto?.fechaPrecioRebajadoHasta],
      imagen: [this.producto?.imagen],

      activo: [this.producto?.activo],
      visibleEnTienda: [this.producto?.visibleEnTienda],

      categoriaId: [this.producto?.categoria?.id, Validators.required],
      materialId: [this.producto?.material?.id, Validators.required],
      colorId: [this.producto?.color?.id, Validators.required],
      usoId: [this.producto?.uso?.id, Validators.required],

    })
  }

  crearProducto() {
    /*     debugger;
        console.log("CC1",this.formProducto.get('colorId')?.value);
        console.log("CC2",this.formProducto.get('materialId')?.value);
        console.log("productos1", this.producto); */
    this.producto.nombre = this.formProducto.get('nombre')?.value;
    this.producto.codigo = this.formProducto.get('codigo')?.value;
    this.producto.descripcion = this.formProducto.get('descripcion')?.value;
    this.producto.medidas = this.formProducto.get('medidas')?.value;
    this.producto.peso = this.formProducto.get('peso')?.value;
    this.producto.imagen = this.formProducto.get('imagen')?.value;
    this.producto.color = find(this.colores, { 'id': +this.formProducto.get('colorId')?.value });
    this.producto.material = find(this.materiales, { 'id': +this.formProducto.get('materialId')?.value });
    this.producto.uso = find(this.usos, { 'id': +this.formProducto.get('usoId')?.value });
    this.producto.categoria = find(this.categorias, { 'id': +this.formProducto.get('categoriaId')?.value });

    // console.log("productos2", this.producto);

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

  update(): void {
    //this.cliente.pedidos = [];
    console.log(this.producto);
    /*     this.producto.peso = this.producto.peso?.toUpperCase();
        this.producto.medidas = this.producto.medidas?.toUpperCase(); */
    this.productoService.updateProducto(this.producto).subscribe(
      json => {
        this.alertService.success(`${json.mensaje}: ${json.producto.nombre}`, 'Producto actualizado');
        this.router.navigate(['/pr/productos']);

      },
      err => {
        // this.errores = err.error.errors as string[];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    )
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
