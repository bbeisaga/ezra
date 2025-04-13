import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Caja } from '../../../../models/caja';
import { Observable } from 'rxjs';
import { Cliente } from '../../../../models/cliente';
import { CajaService } from '../../../../services/caja.service';
import { ClienteService } from '../../../../services/cliente.service';
import { MovimientoService } from '../../../../services/movimiento.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { ActivatedRoute } from '@angular/router';
import { Usuario } from '../../../../models/usuario';
import { AuthService } from '../../../../services/auth.service';
import moment from 'moment';
import { map, switchMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import * as fileSaver from 'file-saver';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-rpte-movimiento-en-caja-por-usuario',
  templateUrl: './rpte-movimiento-en-caja-por-usuario.component.html',
  styleUrl: './rpte-movimiento-en-caja-por-usuario.component.css'
})
export class RpteMovimientoEnCajaPorUsuarioComponent implements OnInit {
  titulo!: string;
  formMov!: FormGroup;
  filtrosReporte!: FiltrosReporte;
  usuario!: Usuario;
  cajaLst: Caja[] = []
  caja!: Caja;

  autocompleteControl = new FormControl();
  filteredOptions!: Observable<Cliente[]>;

  constructor(
    private formBuilder: FormBuilder,
    private cajaService: CajaService,
    private clienteService: ClienteService,
    private movimientoService: MovimientoService,
    private usuarioService: UsuarioService,
    private authService: AuthService

  ) { }

  ngOnInit(): void {
    this.titulo = "Reporte movimiento en caja por usuario";
    this.usuarioService.getUsuarioByUsername( this.authService.usuario.username)
      .subscribe(res => { this.usuario = res}
    );    this.cargarCajas();

    /***********alista la fecha para el campo datetime-local******************* */
    //en un futuro puede sustituirse por timepicker
    var now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());

    this.filtrosReporte = {
      fecha_transaccion1: [moment(now).subtract(1, 'days').toISOString().slice(0, 16)],
      fecha_transaccion2: [now.toISOString().slice(0, 16)],
      caja_id: [],
      usuario_id:[],
      cliente_id:[]
    }

    this.createForm();

    this.filteredOptions = this.autocompleteControl.valueChanges
      .pipe(
        map(value => typeof value === 'string' ? value : value.apellNomRz),
        switchMap(value => value ? this._filter(value) : [])
      );

  }

  private _filter(value: string): Observable<Cliente[]> {
    const filterValue = value.toLowerCase();
    return this.clienteService.filtrarClientes(filterValue);
  }

  mostrarApellidoCliente(cliente: Cliente): string {
    return cliente && cliente.nomApellRz ? cliente.nomApellRz : '';
  }

  seleccionar(event: MatAutocompleteSelectedEvent): void {
    let cliente = event.option.value as Cliente;
    console.log(cliente);
    this.filtrosReporte.cliente_id = [cliente.id]
  }

  generarReporte() {
    this.filtrosReporte.fecha_transaccion1 = [moment(this.formMov.get("fchTimeDesde")?.value).format("YYYY-MM-DD HH:mm")];
    this.filtrosReporte.fecha_transaccion2 = [moment(this.formMov.get("fchTimeHasta")?.value).format("YYYY-MM-DD HH:mm")];
    this.filtrosReporte.caja_id = this.formMov.get("cajasId")?.value;
    this.filtrosReporte.usuario_id = [this.usuario.id]


    const filtros = {
      nombre: this.titulo.replaceAll(" ", ""),
      tipo: "EXCELOPENXML",
      filtros: this.filtrosReporte
    }

    this.movimientoService.ceateReporteMovEnCaja(filtros)
    .subscribe(response => {
      fileSaver.saveAs(response.body!,
        this.filenameFromHeader(response.headers)) //utilidad pra qeu descargue automaticamente
    })
  }

  private filenameFromHeader(headers: HttpHeaders): string {
    const disposition = headers.get('Content-Disposition');
    if (!disposition || disposition.indexOf('filename=') < 0) {
      return '';
    }
    return disposition.substring(disposition.indexOf('filename=') + 9, disposition.length);
  }


  cargarCajas() {
    this.cajaService.getAllCaja().subscribe(res => this.cajaLst = res);
  }


  createForm() {
    this.formMov = this.formBuilder.group(
      {
        fchTimeDesde: [this.filtrosReporte.fecha_transaccion1[0]],
        fchTimeHasta: [this.filtrosReporte.fecha_transaccion2[0]],
        cajasId: [this.filtrosReporte.caja_id[0]],
        cleintesId: [this.filtrosReporte.cliente_id[0]]
      }
    )
  }

}

interface FiltrosReporte {
  fecha_transaccion1: string[],
  fecha_transaccion2: string[],
  caja_id: string[],
  usuario_id: number[],
  cliente_id: number[]

}


