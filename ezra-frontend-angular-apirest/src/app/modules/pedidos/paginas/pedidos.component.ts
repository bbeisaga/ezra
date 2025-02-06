import { Component, OnInit, ViewChild } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';

import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { PedidoService } from '../../../services/pedido.service';
import swal from 'sweetalert2';
import { Pedido } from '../../../models/pedido';
import { Producto } from '../../../models/producto';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ItemPedido } from '../../../models/item-pedido';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AuthService } from '../../../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { findIndex } from 'lodash';
import { MovimientoVentaComponent } from '../../movimientos/pages/movimiento-venta/movimiento-venta.component';
import moment from 'moment';
import { COLOR_ESTADO_PEDIDO } from '../../../constants/pedido.constants';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css'],
})

export class PedidosComponent implements OnInit {

  title:string = 'Listado de pedidos'
  displayedColumns: string[] = ['cliente','documento','createAt', 'entregadoEn','observacion', 'saldoPedido','estado','acciones' ];
  dataSource = new MatTableDataSource<Pedido>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) orberBy!: MatSort;

  pedidos: Pedido[]=[];
  pedidoSeleccionado!: Pedido;

  constructor(
    private pedidoService: PedidoService,
   // private modalService: ModalService,
    public authService: AuthService,
    private dialog: MatDialog,
    private ro: Router,
    private activatedRoute: ActivatedRoute) {

    }

  ngOnInit() {
    this.pedidoService.getAllPedidos().subscribe(response => {
          response.forEach( (r : Pedido) => {
            r.createAt =  moment(r.createAt).format('DD/MM/YYYY');
            r.entregadoEn = moment(r.entregadoEn).format('DD/MM/YYYY');
            r.estadoPedido.color = COLOR_ESTADO_PEDIDO[ (''+ r.estadoPedido.id) as keyof typeof COLOR_ESTADO_PEDIDO];
          })
          //console.log(response);
          this.pedidos = response;
          this.dataSource.data = response;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.orberBy;
        });
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  setPedido (pedido: Pedido): void {
    this.pedidoService.setPedido(pedido);
    this.ro.navigate(['pr/movimientos/venta']);
  }

  updateItem(pedido: Pedido): void {

      const i = findIndex(this.pedidos, (o) => o.id == pedido.id);
      this.pedidos[i] = pedido;
      this.dataSource.data = [...this.pedidos];
    //}
  }

}
