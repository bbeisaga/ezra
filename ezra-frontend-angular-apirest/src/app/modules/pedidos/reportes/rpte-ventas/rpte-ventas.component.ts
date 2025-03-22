import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import moment from 'moment';
import { PedidoService } from '../../../../services/pedido.service';
import { EstadoPedido } from '../../../../models/estado-pedido';
//--instalar file-saver, dos pasas
// 1- npm install --save file-saver
// 2- npm i --save @types/file-saver
import * as fileSaver from 'file-saver';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-rpte-ventas',
  templateUrl: './rpte-ventas.component.html',
  styleUrl: './rpte-ventas.component.css'
})
export class RpteVentasComponent implements OnInit {
  titulo: string = "Reporte de ventas de pedido"
  formVenta!: FormGroup;
  filtrosReporte!: FiltrosReporte;
  estadoPedidoLst: EstadoPedido[] = [];
  pagadoLst: Pagado[] = [];

  constructor(
    private fb: FormBuilder,
    private pedidoService: PedidoService
  ) { }

  ngOnInit(): void {
    this.cargarPagado();
    this.cargarEstadoPedido();
    this.filtrosReporte = {
      create_at1: moment(new Date()).subtract(7, 'days').toISOString(),
      create_at2: new Date().toISOString(),
      estado_pedido_id:-1,
      pagado:-1
    }
    this.createForm();

  }

  generarReporte(){
    this.filtrosReporte.create_at1 = moment(this.formVenta.get("fchDesde")?.value).format("YYYY-MM-DD");
    this.filtrosReporte.create_at2 = moment(this.formVenta.get("fchHasta")?.value).format("YYYY-MM-DD");
    this.filtrosReporte.estado_pedido_id =  this.formVenta.get("estadoPedidoId")?.value
    this.filtrosReporte.pagado = this.formVenta.get("pagado")?.value;

    const filtros = {
      nombre: this.titulo.replaceAll(" ",""),
      tipo:"EXCELOPENXML",
      filtros: this.filtrosReporte
    }

    this.pedidoService.ceateReporteVentas(filtros)
    .subscribe(response => {
      fileSaver.saveAs(response.body!, this.getFilename(response.headers)) //utilidad pra qeu descargue automaticamente
    })
  }

  private getFilename(headers: HttpHeaders): string {
    const disposition = headers.get('Content-Disposition');
    if (!disposition || disposition.indexOf('filename=') < 0) {
      return '';
    }
    return disposition.substring(disposition.indexOf('filename=')+9, disposition.length);
  }

  cargarPagado() {
    this.pagadoLst = [/*{ id: -1, pagado: '--Seleccione' },*/
    { id: 1, pagado: 'Pagado' },
    { id: 0, pagado: 'Pendiente' }]
  }

  cargarEstadoPedido() {
    this.pedidoService.getAllEstadoPedido().subscribe(res => { this.estadoPedidoLst = res; })
  }

  createForm() {
    this.formVenta = this.fb.group(
      {
        fchDesde: [this.filtrosReporte.create_at1],
        fchHasta: [this.filtrosReporte.create_at2],
        estadoPedidoId: [this.filtrosReporte.estado_pedido_id],
        pagado: [this.filtrosReporte.pagado]
      }
    )
  }

}

interface FiltrosReporte {
  create_at1?: string,
  create_at2?: string,
  estado_pedido_id?: number,
  pagado?: number,
}

interface Pagado {
  id: number,
  pagado: string
}
