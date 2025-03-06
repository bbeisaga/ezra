import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../../models/cliente';
import { Region } from '../../../models/region';
import { ClienteService } from '../../../services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { TipoDocumento } from '../../../models/tipo-documento';
import { AlertService } from '../../../services/alert.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  cliente: Cliente = new Cliente();
  tipoDocumentos: TipoDocumento[]=[];
  tipoDocumentoSelected!: TipoDocumento;
  titulo: string = "Crear Cliente";

  errores: string[]=[];

  constructor(private clienteService: ClienteService,
    private router: Router,
    private alertServie: AlertService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('id')!;
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => {this.cliente = cliente});
        console.log("clienteeeeeeeeeee=>", this.cliente);
      }
    });

   // console.log("this.cliente", this.cliente);

    /*     this.clienteService.getRegiones().subscribe(regiones => this.regiones = regiones);
     */

    this.clienteService.getTipoDocumento().subscribe(doc => {
      this.tipoDocumentos = doc
      console.log("documentos=>", this.tipoDocumentos);

      this.tipoDocumentoSelected = this.tipoDocumentos[0]
    });
  }

  create(): void {
    this.cliente.tipoDocumento = this.tipoDocumentoSelected
    this.clienteService.create(this.cliente)
      .subscribe(
        cliente => {
          this.router.navigate(['/pr']);
          this.alertServie.success(`El cliente ${cliente.nombres} ha sido creado con éxito`,'Nuevo cliente' )
        },
/*         err => {
          this.errores = err.error.errors as string[];
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        } */
      );
  }

  update(): void {
    this.cliente.pedidos = [];
    console.log(this.cliente);
    this.clienteService.update(this.cliente)
      .subscribe(
        json => {
          this.router.navigate(['/pr/clientes']);
          this.alertServie.success(`${json.mensaje}: ${json.cliente.nombre}`,'Cliente Actualizado' )
        },
        err => {
          this.errores = err.error.errors as string[];
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        }
      )
  }

  compararDocumento(o1: TipoDocumento, o2: TipoDocumento): boolean {
    if (o1 === undefined && o2 === undefined) {
      return true;
    }

    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.id === o2.id;
  }

}
