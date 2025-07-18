import { AfterViewInit, Component, inject, OnInit } from '@angular/core';
import { find } from 'lodash';
import { environment } from '../../../../environments/environment';
import { Categoria } from '../../../models/categoria';
import { Color } from '../../../models/color';
import { MargenProducto } from '../../../models/margen-producto';
import { Material } from '../../../models/material';
import { Producto } from '../../../models/producto';
import { Uso } from '../../../models/uso';
import { AlertService } from '../../../services/alert.service';
import { AuthService } from '../../../services/auth.service';
import { MediosUtilsService } from '../../../services/medios-utils.service';
import { ProductoService } from '../../../services/producto.service';
import { FormUtils } from '../../../utils/form-utils';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-mantenimiento-producto',
  templateUrl: './mantenimiento-producto.component.html',
  styleUrl: './mantenimiento-producto.component.css',
  standalone: true,
  imports: [CommonModule, MatDatepickerModule, MatTableModule, MatPaginatorModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

})
export class MantenimientoProductoComponent implements OnInit, AfterViewInit {

  public mediosUtilsService = inject(MediosUtilsService);
  producto: Producto = new Producto();
  formProducto!: FormGroup;
  colores: Color[] = [];
  materiales: Material[] = [];
  categorias: Categoria[] = [];
  usos: Uso[] = [];
  verImagenProducto!: string;
  formUtils = FormUtils;

  /*   categoriaId!: number;
    materialId!: number;
    colorId!: number;
    usoId!: number; */
  titulo: string = "Crear Producto";
  //imagenSeleccionada!: File;

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
          this.producto = resp;
          this.createForm();

          this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
        });
      } else {
        this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
        this.createForm();
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
    this.formProducto = this.formBuilder.group({
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
      umbralPocaCantidad: [this.producto?.umbralPocaCantidad, Validators.min(1)],
      umbralCantidadAgotada: [this.producto?.umbralCantidadAgotada, Validators.min(0)],
      cantidadStock: [this.producto?.cantidadStock, Validators.required],
      /*       cantidadStock: [
              { value: this.producto?.cantidadStock, disabled: true },
              { validators: [Validators.required, Validators.min(0)] }
            ], */
      cantidadVendidos: [
        { value: this.producto?.cantidadVendidos, disabled: true },
        { validators: [Validators.required, Validators.min(0)] }
      ],
      minCantidadPedido: [this.producto?.minCantidadPedido, Validators.min(1)],
      maxCantidadPedido: [this.producto?.maxCantidadPedido, Validators.min(1)],
      gruposDe: [this.producto?.gruposDe, Validators.min(1)],
      costoUnitario: [this.producto?.costoUnitario, Validators.min(0)],

      //costoPersonalizacion: [this.producto?.costoPersonalizacion, Validators.min(0)],
      //costoUnitarioEmpaque: [this.producto?.costoUnitarioEmpaque, Validators.min(0)],
      //precioBruto: [this.producto?.precioBruto, Validators.min(3)],
      impuestoIgv: [this.producto?.impuestoIgv, Validators.required],

      //margenGanancia: [this.producto?.margenGanancia, [Validators.required, Validators.min(0)]],
      //precioNeto: [this.producto?.precioNeto, [Validators.required, Validators.min(0)]],
      //precioBrutoRebajado: [this.producto?.precioBrutoRebajado, Validators.min(2)],
      //precioNetoRabajado: [this.producto?.precioNetoRabajado, Validators.min(3)],
      //fechaPrecioRebajadoDesde: [this.producto?.fechaPrecioRebajadoDesde],
      //fechaPrecioRebajadoHasta: [this.producto?.fechaPrecioRebajadoHasta],
      //imagen: [this.producto?.imagen],

      margenesGanancia: this.formBuilder.array([], Validators.minLength(1)),

      /*       margenesGanancia: this.formBuilder.array([
              minCantidad: [this.producto?.margenProducto?.minCantidad, Validators.min(1)],
              maxCantidad: [this.producto?.margenProducto?.maxCantidad],
              margenGanancia: [this.producto?.margenProducto?.margenGanancia, [Validators.required, Validators.min(1)]],
              precioNeto: [this.producto?.margenProducto?.precioNeto, [Validators.required, Validators.min(1)]],
            ], Validators.minLength(1)), */

      activo: [this.producto?.activo],
      visibleEnTienda: [this.producto?.visibleEnTienda],

      categoriaId: [this.producto?.categoria?.id, Validators.required],
      materialId: [this.producto?.material?.id, Validators.required],
      colorId: [this.producto?.color?.id, Validators.required],
      usoId: [this.producto?.uso?.id, Validators.required],

    });

    this.defaultMargenProducto();
  }

  get margenesGanancia() {
    return this.formProducto.get('margenesGanancia') as FormArray;
  }

  defaultMargenProducto() {
    if (this.producto.margenesProducto.length > 0) {
      this.producto.margenesProducto.forEach(m => {
        this.agregrarMargenProducto(this.existMargenProducto(m));
      })
    } /*else {
      this.newMargenProducto();
    }*/
  }

  existMargenProducto(margenProducto: MargenProducto) {
    return this.formBuilder.group({
      id: [margenProducto.id],
      minCantidad: [margenProducto.minCantidad, [Validators.required, Validators.min(1)]],
      maxCantidad: [margenProducto.maxCantidad, Validators.min(1)],
      margen: [margenProducto.margen, [Validators.required, Validators.min(1)]],
      precioNetoSugerido: [margenProducto.precioNetoSugerido, [Validators.required, Validators.min(1)]],
      precioNeto: [margenProducto.precioNeto, [Validators.required, Validators.min(1)]],
    })
  }

  newMargenProducto() {
    const formGroup = this.formBuilder.group({
      minCantidad: [1, Validators.min(1)],
      maxCantidad: [],
      margen: [1, [Validators.required, Validators.min(1)]],
      precioNetoSugerido: [1, [Validators.required, Validators.min(1)]],
      precioNeto: [1, [Validators.required, Validators.min(1)]],
    });
    this.agregrarMargenProducto(formGroup);
  }

  agregrarMargenProducto(formGroup: FormGroup) {
    this.margenesGanancia.push(formGroup);
    const ultimoIndice = (this.margenesGanancia.length - 1);
    this.calcularPrecioNetoPorMargen(ultimoIndice);
  }

  eliminarMargenProducto(index: number) {
    const id: number = + this.margenesGanancia.controls[index].get('id')?.value
    if (id) {
      this.productoService.deleteMargenProducto(id).subscribe(m => {
        this.margenesGanancia.removeAt(index);
      })
    } else {
      this.margenesGanancia.removeAt(index);
    }
  }


  recuperarValForm() {
    this.producto.nombre = this.formProducto.get('nombre')?.value;
    this.producto.codigo = this.formProducto.get('codigo')?.value;
    this.producto.descripcion = this.formProducto.get('descripcion')?.value;
    this.producto.medidas = this.formProducto.get('medidas')?.value;
    this.producto.peso = this.formProducto.get('peso')?.value;
    this.producto.umbralPocaCantidad = this.formProducto.get('umbralPocaCantidad')?.value;
    this.producto.umbralCantidadAgotada = this.formProducto.get('umbralCantidadAgotada')?.value;
    this.producto.cantidadStock = this.formProducto.get('cantidadStock')?.value;
    this.producto.minCantidadPedido = this.formProducto.get('minCantidadPedido')?.value;
    this.producto.maxCantidadPedido = this.formProducto.get('maxCantidadPedido')?.value;
    this.producto.gruposDe = this.formProducto.get('gruposDe')?.value;
    this.producto.costoUnitario = this.formProducto.get('costoUnitario')?.value;
    //this.producto.costoPersonalizacion = this.formProducto.get('costoPersonalizacion')?.value;
    this.producto.impuestoIgv = this.formProducto.get('impuestoIgv')?.value;
    this.producto.margenesProducto = this.margenesGanancia.value
    //this.producto.precioBrutoRebajado = this.formProducto.get('precioBrutoRebajado')?.value;
    //this.producto.precioNetoRabajado = this.formProducto.get('precioNetoRabajado')?.value;
    //this.producto.fechaPrecioRebajadoDesde = this.formProducto.get('fechaPrecioRebajadoDesde')?.value;
    //this.producto.fechaPrecioRebajadoHasta = this.formProducto.get('fechaPrecioRebajadoHasta')?.value;
    this.producto.color = find(this.colores, { 'id': +this.formProducto.get('colorId')?.value });
    this.producto.material = find(this.materiales, { 'id': +this.formProducto.get('materialId')?.value });
    this.producto.uso = find(this.usos, { 'id': +this.formProducto.get('usoId')?.value });
    this.producto.categoria = find(this.categorias, { 'id': +this.formProducto.get('categoriaId')?.value });
    //this.producto.imagen = this.formProducto.get('imagen')?.value;
    this.producto.activo = this.formProducto.get('activo')?.value;
    this.producto.visibleEnTienda = this.formProducto.get('visibleEnTienda')?.value;
  }

  calcularPrecioNeto() {
    const margenesGanancia = this.formProducto.get('margenesGanancia') as FormArray;
    if (margenesGanancia.controls.length > 0) {
      const costoUnitario: number = this.formProducto.get('costoUnitario')?.value;
      const impuestoIgv: number = + this.formProducto.get('impuestoIgv')?.value;
      margenesGanancia.controls.forEach(abstractControl => {
        const margen = + abstractControl.get('margen')?.value
        const precioNetoUnitario: number = costoUnitario * (100 + (impuestoIgv + margen)) / 100;
        abstractControl.get('precioNetoSugerido')?.setValue(precioNetoUnitario.toString());
        abstractControl.get('precioNeto')?.setValue(precioNetoUnitario.toString());
      })
    }
  }

  calcularPrecioNetoPorMargen(index: number) {
    const abstractControl = this.margenesGanancia.controls[index]
    if (abstractControl) {
      const costoUnitario: number = this.formProducto.get('costoUnitario')?.value;
      const impuestoIgv: number = + this.formProducto.get('impuestoIgv')?.value;
      const margen = + abstractControl.get('margen')?.value
      const precioNetoUnitario: number = costoUnitario * (100 + (impuestoIgv + margen)) / 100;
      abstractControl.get('precioNetoSugerido')?.setValue(precioNetoUnitario.toString());
      //abstractControl.get('precioNeto')?.setValue(precioNetoUnitario.toString());
    }
  }

  guardarProducto() {
    //console.log("this.formProducto", this.formProducto.value);
    this.recuperarValForm();
    if (this.producto.margenesProducto.length == 0) {
      this.alertService.warning(`Debe agregar margenes al producto`, 'Producto');
      return;
    }
    if (this.producto.id) {
      this.productoService.updateProducto(this.producto).subscribe(
        json => {
          this.alertService.success(`${json.mensaje}: ${json.producto.nombre}`, 'Producto');
          this.router.navigate(['/productos']);
        }
      )
    } else {
      this.productoService.createProducto(this.producto).subscribe(resp => {
        this.alertService.success('Producto ha sido creado exitosamente', 'Producto');
        this.router.navigate(['/productos']);
      })
    }
  }

  /*   crearProducto() {
      this.productoService.createProducto(this.producto).subscribe(resp => {
        this.alertService.success('Porducto ha sido creado exitosamente', 'Producto');
        this.router.navigate(['/productos']);
      })
    }
  
    update(): void {
      this.productoService.updateProducto(this.producto).subscribe(
        json => {
          this.alertService.success(`${json.mensaje}: ${json.producto.nombre}`, 'Producto actualizado');
          this.router.navigate(['/productos']);
  
        }
      )
    } */


  isImage(fileInput: HTMLInputElement): boolean {
    return this.mediosUtilsService.isImage(fileInput);
  }

  /*   subirImagen(fileInput: HTMLInputElement) {
      if (fileInput && fileInput.files && fileInput.files.length > 0) {
        const imagen: File = fileInput.files[0];
        this.mediosUtilsService.subirImagen(imagen).subscribe(resp => {
          this.verImagenItem = environment.API_URL_VER_IMAGEN + resp.imagen;
          this.item.imagen = resp.imagen;
        })
      }
    } */
  /* 
    seleccionarImagen(event: any) {
      this.imagenSeleccionada = event.target.files[0];
      if (this.imagenSeleccionada!.type.indexOf('image') < 0) {
        this.alertService.error('El archivo debe ser del tipo imagen', 'Imagen');
        return
      }
      this.recuperarValForm();
    } */

  subirImagen(fileInput: HTMLInputElement) {
    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      const imagen: File = fileInput.files[0];
      this.mediosUtilsService.subirImagen(imagen, false).subscribe(resp => {
        this.verImagenProducto = environment.API_URL_VER_IMAGEN + resp.imagen;
        this.producto.imagen = resp.imagen;
      })
    }
  }

  /*   subirImagen(fileInput: HTMLInputElement) {
      this.recuperarValForm();
      if (fileInput && fileInput.files && fileInput.files.length > 0) {
        const imagen: File = fileInput.files[0];
        let formData = new FormData();
        formData.append("archivo", imagen);
        formData.append("producto", JSON.stringify(this.producto));
        formData.append("clienteOnline", 'false');
  
        if (this.producto.id) {
          this.productoService.updateProductoImagen(formData, this.producto.id).subscribe(
            resp => {
              this.producto = resp;
              this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
            })
        } else {
          this.productoService.createProductoImagen(formData).subscribe(
            resp => {
              this.producto = resp;
              this.verImagenProducto = environment.API_URL_VER_IMAGEN + this.producto.imagen;
            })
        }
  
      } else {
        this.alertService.error('Debe colocar un codigo y nombre al producto', 'Imagen');
      }
    } */

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
