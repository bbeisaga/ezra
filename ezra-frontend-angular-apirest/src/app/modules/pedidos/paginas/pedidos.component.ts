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
import { ConfirmarPagoComponent } from '../components/confirmar-pago.component';
import { findIndex } from 'lodash';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css'],
})

export class PedidosComponent implements OnInit {

  title:string = 'Listado de depdidos'
  displayedColumns: string[] = ['cliente','documento','createAt', 'entregadoEn','observacion', 'total', 'pago', 'saldo','estado','acciones' ];
  dataSource = new MatTableDataSource<Pedido>();
  // dataSource = new MatTableDataSource<Cliente>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) orberBy!: MatSort;

  //data : Pedido[]=[];
  pedidos: Pedido[]=[];
  //paginador: any;
  pedidoSeleccionado!: Pedido;

  constructor(
    private pedidoService: PedidoService,
   // private modalService: ModalService,
    public authService: AuthService,
    public dialog: MatDialog,
    private activatedRoute: ActivatedRoute) {

    }

  ngOnInit() {
    this.pedidoService.getAllPedidos()
        .pipe(
    /*       tap(response => {
            console.log('ClientesComponent: tap 3');
            (response.content as Cliente[]).forEach(cliente => console.log(cliente.nombre));
          }) */
        ).subscribe(response => {
          //this.clientes = response.content as Cliente[];
          //this.data = response;
          console.log(response);
          this.pedidos = response;
          this.dataSource.data = response;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.orberBy;
          //this.dataSource = this.clientes
          //console.log(this.dataSource);
          //this.paginador = response;
        });
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialog(pedido:Pedido): void {
    const dialogRef = this.dialog.open(ConfirmarPagoComponent, {
      data: pedido,
      width: '550px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(pedido => {
      if(pedido){
        this.updateItem(pedido);
      }
      console.log('The dialog was closed');
      //this.animal = result;
    });
  }

  updateItem(pedido: Pedido): void {
/*     if(!find(this.data, {usuarioId: item.usuarioId})) {
      this.data.unshift(item);
      this.dataSource = [...this.data];
    } else
    { */
      const i = findIndex(this.pedidos, (o) => o.id == pedido.id);
      this.pedidos[i] = pedido;
      this.dataSource.data = [...this.pedidos];
    //}
  }

/*   delete(cliente: Cliente): void {
    swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido}?`,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {

        this.clienteService.delete(cliente.id).subscribe(
          () => {
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            swal.fire(
              'Cliente Eliminado!',
              `Cliente ${cliente.nombre} eliminado con éxito.`,
              'success'
            )
          }
        )

      }
    });
  } */

/*   abrirModal(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  } */

}
