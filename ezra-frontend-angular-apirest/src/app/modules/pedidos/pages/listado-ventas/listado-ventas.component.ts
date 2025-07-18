import { MatTableModule } from '@angular/material/table';
import { SearchBoxTableComponent } from './../../../compartido/search-box-table/search-box-table.component';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ClienteService } from '../../../../services/cliente.service';

import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import moment from 'moment';
import { concatMap } from 'rxjs';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../../constants/constantes';
import { COLOR_ESTADO_PEDIDO } from '../../../../constants/pedido.constants';
import { Cliente } from '../../../../models/cliente';
import { PageableResponse } from '../../../../models/pageable-response';
import { Pedido } from '../../../../models/pedido';
import { AuthService } from '../../../../services/auth.service';
import { PedidoService } from '../../../../services/pedido.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'listado-ventas',
  templateUrl: './listado-ventas.component.html',
  styleUrls: ['./listado-ventas.component.css'],
  standalone: true,
  imports: [SearchBoxTableComponent, CommonModule, MatTableModule, MatPaginatorModule, MatSortModule, MatFormFieldModule, MatInputModule,RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]
})

export class ListadoVentasComponent implements OnInit, AfterViewInit {

  displayedColumnsLarge: string[] = ['nomApellRz', 'createAt', 'entregadoEn', 'precioNetoTotal', 'pagoTotal', 'vueltoTotal', 'saldoPedido', 'estado', 'acciones'];
  displayedColumnsShort: string[] = [ 'entregadoEn', 'precioNetoTotal', 'pagoTotal','acciones'];

  dataSource: Pedido[] = [];
  pageable: PageableResponse = new PageableResponse();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  querySearch!: string;

  cliente!: Cliente;
  pedidos: Pedido[] = [];
  pedidoSeleccionado!: Pedido;

  constructor(
    private pedidoService: PedidoService,
    // private modalService: ModalService,
    public authService: AuthService,
    public usuarioService: UsuarioService,
    public clienteService: ClienteService,
    private dialog: MatDialog,
    private ro: Router,
    private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    /*     this.usuarioService.getUsuarioByUsername(this.authService.usuario.username).pipe(
          concatMap(usr => this.clienteService.getClienteByUsuarioId(usr.id))
        ).subscribe(cli => {
          this.cliente = cli;
          console.log("cli", this.cliente);
    
        }); */

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

  loadItems(): void {
    try {
      //this.isLoading = true;
      const params: any = {
        active: this.sort.active.toUpperCase(),
        direction: this.sort.direction.toUpperCase(),
        pageNumber: this.paginator.pageIndex,
        pageSize: this.paginator.pageSize,
        query: !!this.querySearch ? this.querySearch : ''
      };
      if (this.authService.hasRole('ROLE_LIST_VENTAS')) {
        this.pedidoService.getAllPedidosPageable(params, 1).subscribe(response => {
          this.dataSource = response.content as Pedido[];
          this.dataSource.forEach((r: Pedido) => {
            r.createAt = moment(r.createAt).format('DD/MM/YYYY');
            r.entregadoEn = r.entregadoEn == null ? '-' : moment(r.entregadoEn).format('DD/MM/YYYY');
            r.adquiridoEn = r.adquiridoEn == null ? '-' : moment(r.adquiridoEn).format('DD/MM/YYYY');
            r.estadoPedido.color = COLOR_ESTADO_PEDIDO[('' + r.estadoPedido.id) as keyof typeof COLOR_ESTADO_PEDIDO];
          })
          this.pedidos = this.dataSource;
          this.pageable = response;
        });

      } else if (this.authService.hasRole('ROLE_LIST_MY_ORDERS')) {
        this.usuarioService.getUsuarioByUsername(this.authService.usuario.username).pipe(
          concatMap(usr => this.clienteService.getClienteByUsuarioId(usr.id)),
          concatMap(cli => this.pedidoService.getPedidosClientePageable(params, cli.id))
        ).subscribe(response => {
          this.dataSource = response.content as Pedido[];
          this.dataSource.forEach((r: Pedido) => {
            r.createAt = moment(r.createAt).format('DD/MM/YYYY');
            r.entregadoEn = r.entregadoEn == null ? '-' : moment(r.entregadoEn).format('DD/MM/YYYY');
            r.adquiridoEn = r.adquiridoEn == null ? '-' : moment(r.adquiridoEn).format('DD/MM/YYYY');
            r.estadoPedido.color = COLOR_ESTADO_PEDIDO[('' + r.estadoPedido.id) as keyof typeof COLOR_ESTADO_PEDIDO];
          })
          this.pedidos = this.dataSource;
          this.pageable = response;
        });
      } else {
        console.log("No tiene permisos ROLE_LIST_VENTAS o ROLE_LIST_MY_ORDERS");
      }
    } catch (error) {
      console.error("Error en la consulta de pedido")
      //this.isLoading = true;
    }

  }

  searchEvent(query: string): void {
    this.querySearch = query;
    this.loadItems();
  }

  setPedido(pedido: Pedido): void {
    this.pedidoService.setPedido(pedido);
    this.ro.navigate(['/movimientos']);
  }


}
