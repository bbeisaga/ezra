import { SearchBoxTableComponent } from './../../compartido/search-box-table/search-box-table.component';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { PageableResponse } from '../../../models/pageable-response';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { UsuarioService } from '../../../services/usuario.service';
import { ModalService } from '../../../services/modal.service';
import { AuthService } from '../../../services/auth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AlertService } from '../../../services/alert.service';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../constants/constantes';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule, Router } from '@angular/router';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css',
  standalone: true,
  imports: [SearchBoxTableComponent, MatDatepickerModule, MatNativeDateModule,MatFormFieldModule, MatInputModule, MatPaginatorModule, MatSortModule, CommonModule, MatDatepickerModule, MatTableModule,  RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]

})
export class UsuariosComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['nomApellRz', 'email', 'acciones'];
  dataSource: Usuario[] = [];
  usuarioSeleccionado!: Usuario;
  pageable: PageableResponse = new PageableResponse();
  querySearch!: string;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  isLoading = true;


  constructor(
    private usuarioService: UsuarioService,
    private modalService: ModalService,
    public authService: AuthService,
    private modal: MatDialog,
    private alertService: AlertService,
    private router: Router) {

  }


  ngOnInit() { }

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


  loadItems() {
    // try {
    this.isLoading = true;
    const params: any = {
      active: this.sort.active.toUpperCase(),
      direction: this.sort.direction.toUpperCase(),
      pageNumber: this.paginator.pageIndex,
      pageSize: this.paginator.pageSize,
      query: !!this.querySearch ? this.querySearch : ''
    };

    this.usuarioService.getAllUsuariosPageable(params).subscribe(response => {
      this.dataSource = response.content as Usuario[];
      this.pageable = response;

    });
  }

  setUsuario(usuario: Usuario): void {
    this.usuarioService.setUsuario(usuario);
    this.router.navigate(['/usuarios/asignar-rol-usuario']);
  }


  searchEvent(query: string): void {
    this.querySearch = query;
    this.loadItems();
  }


  abrirModal(usuario: Usuario) {
    this.usuarioSeleccionado = usuario;
    this.modalService.abrirModal();
  }

}

