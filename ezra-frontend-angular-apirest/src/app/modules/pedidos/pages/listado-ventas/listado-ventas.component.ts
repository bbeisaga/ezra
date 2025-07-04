import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ClienteService } from '../../../../services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';

import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { PedidoService } from '../../../../services/pedido.service';
import swal from 'sweetalert2';
import { Pedido } from '../../../../models/pedido';
import { Producto } from '../../../../models/producto';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ItemPedido } from '../../../../models/item-pedido';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AuthService } from '../../../../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { findIndex } from 'lodash';
import { MovimientoComponent } from '../../../movimientos/pages/movimiento/movimiento.component';
import moment from 'moment';
import { COLOR_ESTADO_PEDIDO } from '../../../../constants/pedido.constants';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../../constants/constantes';
import { PageableResponse } from '../../../../models/pageable-response';

@Component({
  selector: 'listado-ventas',
  templateUrl: './listado-ventas.component.html',
  styleUrls: ['./listado-ventas.component.css'],
})

export class ListadoVentasComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['nomApellRz','createAt', 'entregadoEn','precioNetoTotal','pagoTotal','vueltoTotal', 'saldoPedido', 'estado','acciones' ];
  dataSource: Pedido[]=[];
  pageable: PageableResponse = new PageableResponse();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  querySearch!: string;

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

  ngOnInit():void {

  }

  ngAfterViewInit(): void {
        this.sort.sortChange.subscribe(() => {
          (this.paginator.pageIndex = 0);
          this.loadItems();
        });
        this.paginator._intl.itemsPerPageLabel = ELEMENTOS_POR_PAGINA;
        this.paginator._intl.firstPageLabel = PRIMERA_PAGINA;
        this.paginator._intl.nextPageLabel = SIGUIENTE_PAGINA;
        this.paginator._intl.lastPageLabel = ULTIMA_PAGINA;
        this.loadItems();
  }

  loadItems():void {
     try {
        //this.isLoading = true;
        const params: any = {
          active: this.sort.active.toUpperCase(),
          direction: this.sort.direction.toUpperCase(),
          pageNumber: this.paginator.pageIndex,
          pageSize: this.paginator.pageSize,
          query: !!this.querySearch ? this.querySearch:''
        };

        this.pedidoService.getAllPedidosPageable(params,1).subscribe(response => {
          this.dataSource = response.content as Pedido[];
          this.dataSource.forEach( (r : Pedido) => {
            r.createAt =  moment(r.createAt).format('DD/MM/YYYY');
            r.entregadoEn = r.entregadoEn==null?'-':moment(r.entregadoEn).format('DD/MM/YYYY');
            r.adquiridoEn = r.adquiridoEn==null?'-':moment(r.adquiridoEn).format('DD/MM/YYYY');
            r.estadoPedido.color = COLOR_ESTADO_PEDIDO[ (''+ r.estadoPedido.id) as keyof typeof COLOR_ESTADO_PEDIDO];
          })
          this.pedidos = this.dataSource;
          this.pageable = response;

        });
      } catch(error){
        console.error("Error en la consulta de pedido")
        //this.isLoading = true;
      }

  }

  searchEvent(query: string): void {
    this.querySearch = query;
    this.loadItems();
  }

  setPedido (pedido: Pedido): void {
    this.pedidoService.setPedido(pedido);
    this.ro.navigate(['/movimientos']);
  }


}
