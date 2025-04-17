import { Component, OnInit } from '@angular/core';
import { Role } from '../../../../models/role';
import { Modulo } from '../../../../models/modulo';
import { RolService } from '../../../../services/rol.service';
import { ThemePalette } from '@angular/material/core';
import { UsuarioService } from '../../../../services/usuario.service';
import { Usuario } from '../../../../models/usuario';
import { findIndex } from 'lodash';

@Component({
  selector: 'app-asignar-rol-usuario',
  templateUrl: './asignar-rol-usuario.component.html',
  styleUrl: './asignar-rol-usuario.component.css'
})
export class AsignarRolUsuarioComponent implements OnInit {

  roles: string[] = [];
  modulos: Modulo[] = [];
  usuarioSeleccionado!: Usuario;

  constructor(private rolService: RolService,
    private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.usuarioSeleccionado = this.usuarioService.usuario;
    console.log("usuarioSeleccionado", this.usuarioSeleccionado);
    this.rolService.modulos().subscribe(result => this.modulos = result);
  }
}






