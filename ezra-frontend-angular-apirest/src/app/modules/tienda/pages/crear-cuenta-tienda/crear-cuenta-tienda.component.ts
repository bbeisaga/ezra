import { AlertService } from './../../../../services/alert.service';
import { Component, inject } from '@angular/core';
import { TipoDocumento } from '../../../../models/tipo-documento';
import { Cliente } from '../../../../models/cliente';
import { ClienteService } from '../../../../services/cliente.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../../../../models/usuario';
import { find } from 'lodash';
import { FormUtils } from '../../../../utils/form-utils';

@Component({
  selector: 'app-crear-cuenta-tienda',
  templateUrl: './crear-cuenta-tienda.component.html',
  styleUrl: './crear-cuenta-tienda.component.css'
})
export class CrearCuentaTiendaComponent {


  private clienteService = inject(ClienteService);
  private alertService = inject(AlertService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  formUtils = FormUtils;

  tipoDocumentos: TipoDocumento[] = [];
  //usuario: Usuario = new Usuario();
  cliente: Cliente = new Cliente();
  formNewCuenta!: FormGroup;

  ngOnInit() {
    this.createForm();
    this.clienteService.getTipoDocumento().subscribe(doc => {
      this.tipoDocumentos = doc
      console.log("aa:", this.tipoDocumentos);
    });
  }

  createForm(): void {
    this.formNewCuenta = this.formBuilder.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      documentoId: [this.cliente?.tipoDocumento?.id, Validators.required],

      numeroDocumento: [this.cliente?.numeroDocumento,
      { validators: [Validators.required, Validators.minLength(8), Validators.maxLength(11), Validators.pattern('^\\d+$')] }
      ],
      nomApellRz: [this.cliente?.nomApellRz,
      { validators: [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-Z\\s]+$')] }
      ],

      direccion: [this.cliente?.direccion,
      { validators: [Validators.required, Validators.minLength(5)] }
      ],
      celular: [this.cliente?.celular,
      { validators: [Validators.required, Validators.minLength(6), Validators.maxLength(13), Validators.pattern('^\\d+$')] }
      ],
      /*       clave: [this.cliente?.usuario?.password,
            { validators: [Validators.required, Validators.minLength(4), Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')] }
      
            ],
            confirmaClave: [this.cliente?.usuario?.confirmaPassword,
            { validators: [Validators.required, Validators.minLength(4), Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')] }
            ] */


      clave: [this.cliente?.clave,
      { validators: [Validators.required, Validators.minLength(4)] }

      ],
      confirmaClave: [this.cliente?.confirmaClave,
      { validators: [Validators.required, Validators.minLength(4)] }
      ]

    })
  }

  crearCuenta() {
    if (this.formNewCuenta.invalid) {
      this.formNewCuenta.markAllAsTouched();
      return;
    }


    if (this.formNewCuenta.valid) {
      const clave = this.formNewCuenta.get('clave')?.value
      const confirmaClave = this.formNewCuenta.get('confirmaClave')?.value
      if (clave === confirmaClave) {
        this.cliente.tipoDocumento = find(this.tipoDocumentos, (td) => td.id === +this.formNewCuenta.get('documentoId')?.value)!
        this.cliente.numeroDocumento = this.formNewCuenta.get('numeroDocumento')?.value
        this.cliente.nomApellRz = this.formNewCuenta.get('nomApellRz')?.value
        this.cliente.direccion = this.formNewCuenta.get('direccion')?.value
        this.cliente.celular = this.formNewCuenta.get('celular')?.value
        this.cliente.clave = clave;
        this.clienteService.create(this.cliente).subscribe(resp => {
          this.alertService.success(`Hola ${resp.nomApellRz} se ha creado tu cuenta, puedes iniciar sesión`, "Exito");
          this.router.navigate(['/login']);
        })

      } else {
        this.alertService.error("No coinciden las claves de registro", "Error");
      }
    }
  }

  //HEMOS REMPLAZADO LA VALDIACION EN UNA CLASE REUTILIZABLE PARA TODOS UTILS
/*   isValidField(fieldName: string): boolean | null {
    return (this.formNewCuenta.controls[fieldName].errors && this.formNewCuenta.controls[fieldName].touched)
  }

  getFieldError(fieldName: string): string | null {
    if (!this.formNewCuenta.controls[fieldName]) return null;
    const errors = this.formNewCuenta.controls[fieldName].errors || {}
    for (const key of Object.keys(errors)) {
      switch (key) {
        case 'required': return 'Campo requerido';
        case 'minlength': return `Mínimo de ${errors['minlength'].requiredLength} caracteres`;
        case 'maxlength': return `Máximo de ${errors['maxlength'].requiredLength} caracteres`;
        case 'pattern': return `No cumple requerimientos de campo`;
        case 'min': return `Valor mínimo de ${errors['min'].min}`;
      }
    }
    return null;
  } */

  resetForm() {
    this.formNewCuenta.reset();
  }

}
