import { Component, OnInit, ViewChild } from '@angular/core';

import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Cliente } from '../../../models/cliente';
import { ModalService } from '../../../services/modal.service';
import { AuthService } from '../../../services/auth.service';
import { ClienteService } from '../../../services/cliente.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],

})
export class ClientesComponent implements OnInit {

  displayedColumns: string[] = ['apellidos','nombres', 'numeroDocumento','celular','acciones' ];
  dataSource!: MatTableDataSource<Cliente>;
  // dataSource = new MatTableDataSource<Cliente>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) orberBy!: MatSort;


  clientes: Cliente[]=[];
  //paginador: any;
  clienteSeleccionado!: Cliente;

  constructor(
    private clienteService: ClienteService,
    private modalService: ModalService,
    public authService: AuthService,
    private activatedRoute: ActivatedRoute) {

    }

  ngOnInit() {
      this.clienteService.getAllClientes()
        .pipe(
    /*       tap(response => {
            console.log('ClientesComponent: tap 3');
            (response.content as Cliente[]).forEach(cliente => console.log(cliente.nombre));
          }) */
        ).subscribe(response => {
          //this.clientes = response.content as Cliente[];
          this.clientes = response;
          this.dataSource = new MatTableDataSource(this.clientes);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.orberBy;
          //this.dataSource = this.clientes
          //console.log(this.dataSource);
          //this.paginador = response;
        });
    //});
/*
    this.modalService.notificarUpload.subscribe(cliente => {
      this.clientes = this.clientes.map(clienteOriginal => {
        if (cliente.id == clienteOriginal.id) {
          clienteOriginal.foto = cliente.foto;
        }
        return clienteOriginal;
      })
    }) */
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  delete(cliente: Cliente): void {
    swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente ${cliente.nombres} ${cliente.apellidos}?`,
//       type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    //  confirmButtonClass: 'btn btn-success',
    //  cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {

        this.clienteService.delete(cliente.id).subscribe(
          () => {
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            swal.fire(
              'Cliente Eliminado!',
              `Cliente ${cliente.nombres} eliminado con éxito.`,
              'success'
            )
          }
        )

      }
    });
  }

  abrirModal(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  }

}
