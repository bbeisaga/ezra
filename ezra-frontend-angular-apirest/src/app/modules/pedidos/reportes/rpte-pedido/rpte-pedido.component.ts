import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, OnInit } from '@angular/core';
import moment from 'moment';
import { PedidoService } from '../../../../services/pedido.service';
import { EstadoPedido } from '../../../../models/estado-pedido';
//--instalar file-saver, dos pasas
// 1- npm install --save file-saver
// 2- npm i --save @types/file-saver
import * as fileSaver from 'file-saver';
import { HttpHeaders } from '@angular/common/http';
import { TipoPedido } from '../../../../models/tipo-pedido';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-rpte-pedido',
  templateUrl: './rpte-pedido.component.html',
  styleUrl: './rpte-pedido.component.css',
  standalone: true,
    imports: [ CommonModule, MatDatepickerModule, MatNativeDateModule,MatFormFieldModule, MatInputModule, MatTableModule, MatPaginatorModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

})
export class RptePedidoComponent implements OnInit {
  titulo!: string;
  formVenta!: FormGroup;
  filtrosReporte!: FiltrosReporte;
  estadoPedidoLst: EstadoPedido[] = [];
  tipoPedido!: TipoPedido;
  pagadoLst: Pagado[] = [];

  constructor(
    private fb: FormBuilder,
    private pedidoService: PedidoService,
    private ar: ActivatedRoute,
  ) { }

  ngOnInit(): void {

    this.ar.paramMap.subscribe(params => {
      let tipoPedidoId = +params.get('tipoPedidoId')! ;
      this.pedidoService.getTipoPedido(tipoPedidoId)
      .subscribe(tipoPedido => { this.tipoPedido = tipoPedido
        this.titulo = `Reporte de ${this.tipoPedido.nombre}`
        }
      );
    });

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
      tipoPedido: this.tipoPedido.id,
      tipo:"EXCELOPENXML",
      filtros: this.filtrosReporte
    }

    this.pedidoService.ceateReportePorTipoPedido(filtros)
    .subscribe(response => {
      fileSaver.saveAs(response.body!,
        this.pedidoService.filenameFromHeader(response.headers)) //utilidad pra qeu descargue automaticamente
    })
  }


  cargarPagado() {
    this.pagadoLst = [/*{ id: -1, pagado: '--Seleccione' },*/
    { id: 1, pagado: 'Si' },
    { id: 0, pagado: 'No' }]
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
