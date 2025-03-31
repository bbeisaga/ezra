import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import moment from 'moment';
import { Caja } from '../../../../models/caja';
import { Usuario } from '../../../../models/usuario';
import { CajaService } from '../../../../services/caja.service';
import { UsuarioService } from '../../../../services/usuario.service';
import * as fileSaver from 'file-saver';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-rpte-caja',
  templateUrl: './rpte-caja.component.html',
  styleUrl: './rpte-caja.component.css'
})
export class RpteCajaComponent implements OnInit {
  titulo!: string;
  formCierreCaja!: FormGroup;
  filtrosReporte!: FiltrosReporte;
  //estadoPedidoLst: EstadoPedido[] = [];
  usuarioLst: Usuario[] = [];
  usuario!: Usuario;
  cajaLst: Caja[] = []
  caja!: Caja;
  estadoCajaUsuarioLst: EstadoCajaUsuario[] = [];
  username!: string;

  constructor(
    private formBuilder: FormBuilder,
    private cajaService: CajaService,
    private usuarioService: UsuarioService,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.titulo = "Reporte cierre de caja";
    this.cargarEstadoCajaUsuario();
    this.cargarCajas();
    this.cargarUsuarios();
    this.filtrosReporte = {
      fecha_apertura1: [moment(new Date()).subtract(2, 'days').toISOString()],
      fecha_apertura2: [new Date().toISOString()],
      activa: [],
      caja_id:[],
      usuario_id:[]
    }

    this.createForm();

  }

   generarReporte() {
    this.filtrosReporte.fecha_apertura1 = [moment(this.formCierreCaja.get("fchDesde")?.value).format("YYYY-MM-DD")];
    this.filtrosReporte.fecha_apertura2 = [moment(this.formCierreCaja.get("fchHasta")?.value).format("YYYY-MM-DD")];
    this.filtrosReporte.activa = this.formCierreCaja.get("estadoCajaUsuario")?.value;
    this.filtrosReporte.caja_id=this.formCierreCaja.get("cajasId")?.value;
    this.filtrosReporte.usuario_id=this.formCierreCaja.get("usuariosId")?.value;


    const filtros = {
      nombre: this.titulo.replaceAll(" ", ""),
      tipo: "EXCELOPENXML",
      filtros: this.filtrosReporte
    }

    this.cajaService.ceateReporteCierreCaja(filtros)
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
      return disposition.substring(disposition.indexOf('filename=')+9, disposition.length);
    }

  cargarEstadoCajaUsuario() {
    this.estadoCajaUsuarioLst = [/*{ id: -1, pagado: '--Seleccione' },*/
      { id: 1, nombre: 'Activa' },
      { id: 0, nombre: 'Cerrada' }]
  }

  cargarCajas() {
    this.cajaService.getAllCaja().subscribe(res => this.cajaLst = res);
  }

  cargarUsuarios(){
    this.usuarioService.getAllUsers().subscribe(res => this.usuarioLst = res);
  }

  createForm() {
    this.formCierreCaja = this.formBuilder.group(
      {
        fchDesde: [this.filtrosReporte.fecha_apertura1[0]],
        fchHasta: [this.filtrosReporte.fecha_apertura2[0]],
        estadoCajaUsuario: [this.filtrosReporte.activa[0]],
        cajasId:[this.filtrosReporte.caja_id[0]],
        usuariosId:[this.filtrosReporte.usuario_id[0]]
      }
    )
  }

}

interface FiltrosReporte {
  fecha_apertura1: string[],
  fecha_apertura2: string[],
  activa: string[]
  caja_id:string[],
  usuario_id:number[],
}

interface EstadoCajaUsuario {
  id: number,
  nombre: string
}

