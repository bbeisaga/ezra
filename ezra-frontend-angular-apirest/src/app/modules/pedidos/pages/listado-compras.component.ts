import { SearchBoxTableComponent } from './../../compartido/search-box-table/search-box-table.component';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Pedido } from '../../../models/pedido';
import { PageableResponse } from '../../../models/pageable-response';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { PedidoService } from '../../../services/pedido.service';
import { AuthService } from '../../../services/auth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import moment from 'moment';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../constants/constantes';
import { COLOR_ESTADO_PEDIDO } from '../../../constants/pedido.constants';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'listado-compras',
  templateUrl: './listado-compras.component.html',
  styleUrl: './listado-compras.component.css',
  standalone: true,
  imports: [SearchBoxTableComponent, CommonModule, MatTableModule, MatPaginatorModule, MatSortModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

})
export class ListadoComprasComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['nomApellRz', 'createAt', 'adquiridoEn', 'costoNetoTotal', 'pagoTotal', 'vueltoTotal', 'saldoPedido', 'estado', 'acciones'];
  dataSource: Pedido[] = [];
  pageable: PageableResponse = new PageableResponse();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  querySearch!: string;

  pedidos: Pedido[] = [];
  pedidoSeleccionado!: Pedido;

  constructor(
    private pedidoService: PedidoService,
    public authService: AuthService,
    private dialog: MatDialog,
    private ro: Router,
    private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {

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

      this.pedidoService.getAllPedidosPageable(params, 2).subscribe(response => {
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
    } catch (error) {
      console.error("Error en la consulta de pedido")
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
