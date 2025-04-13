import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Caja } from '../../../../models/caja';
import { CajaService } from '../../../../services/caja.service';
import { ActivatedRoute } from '@angular/router';
import moment from 'moment';
import { HttpHeaders } from '@angular/common/http';
import * as fileSaver from 'file-saver';
import { Cliente } from '../../../../models/cliente';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { ClienteService } from '../../../../services/cliente.service';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MovimientoService } from '../../../../services/movimiento.service';
import { Usuario } from '../../../../models/usuario';
import { UsuarioService } from '../../../../services/usuario.service';

@Component({
  selector: 'app-rpte-movimiento-en-caja',
  templateUrl: './rpte-movimiento-en-caja.component.html',
  styleUrl: './rpte-movimiento-en-caja.component.css'
})
export class RpteMovimientoEnCajaComponent implements OnInit {
  titulo!: string;
  formMov!: FormGroup;
  filtrosReporte!: FiltrosReporte;
  usuarioLst: Usuario[] = [];
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
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.titulo = "Reporte movimiento en caja";
    //this.cargarEstadoCajaUsuario();
    this.cargarCajas();
    this.cargarUsuarios();

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
    this.filtrosReporte.usuario_id = this.formMov.get("usuariosId")?.value;


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

  cargarUsuarios(){
    this.usuarioService.getAllUsers().subscribe(res => this.usuarioLst = res);
  }

  createForm() {
    this.formMov = this.formBuilder.group(
      {
        fchTimeDesde: [this.filtrosReporte.fecha_transaccion1[0]],
        fchTimeHasta: [this.filtrosReporte.fecha_transaccion2[0]],
        cajasId: [this.filtrosReporte.caja_id[0]],
        usuariosId: [this.filtrosReporte.usuario_id[0]],
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

