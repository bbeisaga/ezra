import { Component } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../../../services/alert.service';
import { AuthService } from '../../../services/auth.service';
import { Producto } from '../../../models/producto';
import { FormBuilder, FormGroup } from '@angular/forms';
import { environment } from '../../../../environments/environment';


@Component({
  selector: 'app-mantenimiento-producto',
  templateUrl: './mantenimiento-producto.component.html',
  styleUrl: './mantenimiento-producto.component.css'
})
export class MantenimientoProductoComponent {

  producto: Producto = new Producto();
  formProducto!: FormGroup;


  private imagenSeleccionada!: File;

  constructor(private productoService: ProductoService,
    private router: Router,
    private alertService: AlertService,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) { }


  createForm(): void {

    // console.log("cajaUsuariocreateForm", this.cajaUsuario);
    this.formProducto = this.formBuilder.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      nombre: [this.producto?.nombre],
      descripcion: [this.producto?.descripcion],
      //seleccionarImagen: [this.producto?.imagen],

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

}
