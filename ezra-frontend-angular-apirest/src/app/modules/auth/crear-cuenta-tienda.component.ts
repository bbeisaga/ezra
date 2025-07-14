import { Component, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { find } from 'lodash';
import { ClienteService } from '../../services/cliente.service';
import { AlertService } from '../../services/alert.service';
import { FormUtils } from '../../utils/form-utils';
import { Cliente } from '../../models/cliente';
import { TipoDocumento } from '../../models/tipo-documento';

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
      tipoDocumentoId: [this.cliente?.tipoDocumento?.id, Validators.required],

      numeroDocumento: [this.cliente?.numeroDocumento,
      { validators: [Validators.required, Validators.minLength(8), Validators.maxLength(11), Validators.pattern('^\\d+$')] }
      ],
      nomApellRz: [this.cliente?.nomApellRz,
      { validators: [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-Z\\s]+$')] }
      ],

      direccion: [this.cliente?.direccion,
      { validators: [Validators.required, Validators.minLength(5)] }
      ],
      celular: [this.cliente?.celular, [Validators.required, Validators.minLength(6), Validators.maxLength(14), Validators.pattern('^\\d+$')]],
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

  findByNumDocumento(event: any) {
    const numero = event.target.value;
    const tipoDocumentoId = this.formNewCuenta.get('tipoDocumentoId')?.value;
    this.clienteService.getNumeroDocumento(numero).subscribe(resp => {
      this.cliente = resp;
      this.createForm();

    }, err => {
      console.log("Entro")
    }, () => {
      this.formNewCuenta.get('tipoDocumentoId')?.setValue(tipoDocumentoId);
      this.formNewCuenta.get('numeroDocumento')?.setValue(numero);
    })

  }

    findByCelular(event: any) {
      //debugger;
    const celular = event.target.value;
    const tipoDocumentoId = this.formNewCuenta.get('tipoDocumentoId')?.value;
    const numeroDocumento = this.formNewCuenta.get('numeroDocumento')?.value;
    const nomApellRz = this.formNewCuenta.get('nomApellRz')?.value;
    const direccion = this.formNewCuenta.get('direccion')?.value;

    this.clienteService.getCelular(celular).subscribe(resp => {
      this.cliente = resp;
      this.createForm();

    }, () => {
      this.formNewCuenta.get('tipoDocumentoId')?.setValue(tipoDocumentoId);
      this.formNewCuenta.get('numeroDocumento')?.setValue(numeroDocumento);
      this.formNewCuenta.get('nomApellRz')?.setValue(nomApellRz);
      this.formNewCuenta.get('direccion')?.setValue(direccion);
      this.formNewCuenta.get('celular')?.setValue(celular);
    })

  }

  resetForm() {
    this.formNewCuenta.reset();
  }

}
