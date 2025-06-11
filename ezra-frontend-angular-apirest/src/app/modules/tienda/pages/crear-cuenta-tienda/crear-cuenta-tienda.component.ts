import { Component, inject } from '@angular/core';
import { TipoDocumento } from '../../../../models/tipo-documento';
import { Cliente } from '../../../../models/cliente';
import { ClienteService } from '../../../../services/cliente.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-crear-cuenta-tienda',
  templateUrl: './crear-cuenta-tienda.component.html',
  styleUrl: './crear-cuenta-tienda.component.css'
})
export class CrearCuentaTiendaComponent {

  private clienteService = inject(ClienteService);
  private formBuilder = inject(FormBuilder);

  tipoDocumentos: TipoDocumento[] = [];
  cliente: Cliente = new Cliente();
  formNewCuenta!: FormGroup;

  ngOnInit() {
    this.createForm();
    this.clienteService.getTipoDocumento().subscribe(doc => {
      this.tipoDocumentos = doc
    });
  }

  createForm(): void {
    this.formNewCuenta = this.formBuilder.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      documentoId: [this.cliente?.tipoDocumento.id, Validators.required],
      numeroDocumento: [this.cliente?.numeroDocumento,
      { validators: [Validators.required, Validators.minLength(8), Validators.maxLength(11), Validators.pattern('^\\d+$')] }
      ],
      nomApellRz: [this.cliente?.nomApellRz,
      { validators: [Validators.required, Validators.minLength(2), Validators.pattern('[^a-zA-Z\\s]+')] }
      ],
      direccion: [this.cliente?.direccion,
      { validators: [Validators.required, Validators.minLength(5)] }
      ],
      celular: [this.cliente?.celular,
      { validators: [Validators.required, Validators.minLength(6), Validators.maxLength(13), Validators.pattern('^\\d+$')] }
      ],
      clave: [this.cliente?.usuario.password,
      { validators: [Validators.required, Validators.minLength(4), Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')] }
      ],
      confirmaClave: [this.cliente?.usuario.confirmaPassword,
      { validators: [Validators.required, Validators.minLength(4), Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')] }
      ]
      /* cantidadStock: [
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
      usoId: [this.producto?.uso?.id, Validators.required], */

    })
  }

}
