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
import { Producto } from '../../../models/producto';
import { ProductoService } from '../../../services/producto.service';
import { find } from 'lodash';
import {  GenericosDeProducto } from '../../../models/genericos-de-producto';
import { GenericosDeProductoService } from '../../../services/genericos-de-producto.service';
import { Color } from '../../../models/color';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css'],

})
export class ProductoComponent implements OnInit , AfterViewInit{

  displayedColumns: string[] = ['id', 'nombre','color','material','categoria','uso','cantidadStock', 'costoUnitario','acciones'];
  genericosDeProducto:GenericosDeProducto[]=[];
  dataSource : Producto[]=[];
  productos:Producto[]=[];
  pageable: PageableResponse = new PageableResponse();
  querySearch!: string;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  isLoading = true;

  constructor(
    private genericosDeProductoService: GenericosDeProductoService,
    private productoService: ProductoService,
    private modalService: ModalService,
    public authService: AuthService,
    private modal: MatDialog,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute) {

    }


  ngOnInit() {

    this.genericosDeProductoService.getGenericos().subscribe(result =>this.genericosDeProducto = result)
    console.log("ngOnInit", this.genericosDeProducto);
  }

  ngAfterViewInit(): void {
    console.log("ngAfterViewInit", this.genericosDeProducto);
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
    console.log("loadItems", this.genericosDeProducto);

      this.isLoading = true;
      const params: any = {
        active: this.sort.active.toUpperCase(),
        direction: this.sort.direction.toUpperCase(),
        pageNumber: this.paginator.pageIndex,
        pageSize: this.paginator.pageSize,
        query: !!this.querySearch ? this.querySearch:''
      };

      this.productoService.getAllProductosPageable(params).subscribe(response => {
        this.productos = response.content as Producto[]
        this.productos.forEach( p => {
/*             p.color = Object.assign({}, this.findObjectInGenericos(p.colorId!));
            p.material = Object.assign({}, this.findObjectInGenericos(p.materialId!));
            p.origen = Object.assign({}, this.findObjectInGenericos(p.origenId!));
            p.empaque = Object.assign({}, this.findObjectInGenericos(p.empaqueId!));
            p.categoria = Object.assign({}, this.findObjectInGenericos(p.categoriaId!));
            p.uso = Object.assign({}, this.findObjectInGenericos(p.usoId!)); */
          }
        );
        this.dataSource = this.productos;
        console.log("this.dataSource", this.dataSource);
        this.pageable = response;
       /* this.dataSource = new MatTableDataSource(this.clientes);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.orberBy; */
      });
  }

  findObjectInGenericos( id:number ){
    console.log("findObjectInGenericos", this.genericosDeProducto);
    return find(this.genericosDeProducto, {"id": id})
  }


/*   applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  } */

  searchEvent(query: string): void {
    this.querySearch = query;
    this.loadItems();
  }

  delete(producto: Producto): void {

    const dialogRef = this.alertService.decision(`¿Seguro que desea eliminar ${producto.nombre}?`,"Borrar producto")
      dialogRef.afterClosed().subscribe((result:boolean) => {
        if (result) {
            this.productoService.deleteProducto(producto.id).subscribe(
              () => {
                this.loadItems();
                ///this.dataSource = this.clientes.filter(cli => cli !== cliente)
                this.alertService.success(`${producto.nombre} eliminado con éxito.`,'Producto Eliminado!')
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

/*   abrirModal(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  } */

}
