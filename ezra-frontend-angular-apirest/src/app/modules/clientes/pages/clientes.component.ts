import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';

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
import { PageableParams } from '../../../models/pageable-params';
import { PageableResponse } from '../../../models/pageable-response';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../constants/constantes';
import { MatDialog } from '@angular/material/dialog';
import { AlertService } from '../../../services/alert.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],

})
export class ClientesComponent implements OnInit , AfterViewInit{

  displayedColumns: string[] = ['apellidos','nombres','createAt' ,'numeroDocumento','celular','acciones' ];
  //dataSource!: MatTableDataSource<Cliente>;
  dataSource : Cliente[]=[];
 //clientes: Cliente[]=[];
  clienteSeleccionado!: Cliente;
  pageable: PageableResponse = new PageableResponse();
  querySearch!: string;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  isLoading = true;


  constructor(
    private clienteService: ClienteService,
    private modalService: ModalService,
    public authService: AuthService,
    private modal: MatDialog,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute) {

    }


  ngOnInit() {

    //this.loadtemporal()
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


  loadItems(){
   // try {
      this.isLoading = true;
      const params: any = {
        active: this.sort.active.toUpperCase(),
        direction: this.sort.direction.toUpperCase(),
        pageNumber: this.paginator.pageIndex,
        pageSize: this.paginator.pageSize,
        query: !!this.querySearch ? this.querySearch:''
      };
/*       console.log(!!this.querySearch);
      if(!!this.querySearch ){
        params.query = this.querySearch
      } */

      this.clienteService.getAllClientesPageable(params).subscribe(response => {
        this.dataSource = response.content as Cliente[];
        //this.clientes = this.dataSource;
        this.pageable = response;

       /* this.dataSource = new MatTableDataSource(this.clientes);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.orberBy; */

      });
/*       this.isLoading = false;
    } catch (error) {
      console.log("error")

      this.isLoading = true;
    } */

  }


/*   applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  } */

  searchEvent(query: string): void {
    this.querySearch = query;
    this.loadItems();
  }

  delete(cliente: Cliente): void {

    const dialogRef = this.alertService.decision(`¿Seguro que desea eliminar al cliente ${cliente.nombres} ${cliente.apellidos}?`,"Borrar cliente")
      dialogRef.afterClosed().subscribe((result:boolean) => {
        if (result) {
            this.clienteService.delete(cliente.id).subscribe(
              () => {
                this.loadItems();
                ///this.dataSource = this.clientes.filter(cli => cli !== cliente)
                this.alertService.success(`Cliente ${cliente.nombres} eliminado con éxito.`,'Cliente Eliminado!')
                 /* swal.fire(
                  'Cliente Eliminado!',
                  `Cliente ${cliente.nombres} eliminado con éxito.`,
                  'success'
                ) */
              }
            )
        }
      });


/*


     swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente ${cliente.nombres} ${cliente.apellidos}?`,
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
            this.dataSource = this.clientes.filter(cli => cli !== cliente)
            swal.fire(
              'Cliente Eliminado!',
              `Cliente ${cliente.nombres} eliminado con éxito.`,
              'success'
            )
          }
        )

      }
    });  */
  }

  abrirModal(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  }

}
