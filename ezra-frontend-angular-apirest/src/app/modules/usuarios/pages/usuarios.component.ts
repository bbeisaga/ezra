import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { PageableResponse } from '../../../models/pageable-response';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UsuarioService } from '../../../services/usuario.service';
import { ModalService } from '../../../services/modal.service';
import { AuthService } from '../../../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { AlertService } from '../../../services/alert.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ELEMENTOS_POR_PAGINA, PRIMERA_PAGINA, SIGUIENTE_PAGINA, ULTIMA_PAGINA } from '../../../constants/constantes';


@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit , AfterViewInit{

  displayedColumns: string[] = ['apellidos','nombres' ,'email','acciones'];
  dataSource : Usuario[]=[];
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


  ngOnInit() {}

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

      this.usuarioService.getAllUsuariosPageable(params).subscribe(response => {
        this.dataSource = response.content as Usuario[];
        this.pageable = response;

      });
  }

    setUsuario (usuario: Usuario): void {
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

