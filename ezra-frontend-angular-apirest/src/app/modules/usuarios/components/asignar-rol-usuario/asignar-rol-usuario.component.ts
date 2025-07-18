import { CheckboxUsuarioRolComponent } from './../checkbox-usuario-rol/checkbox-usuario-rol.component';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { Router, RouterModule } from '@angular/router';
import { findIndex } from 'lodash';
import { Modulo } from '../../../../models/modulo';
import { Role } from '../../../../models/role';
import { Usuario } from '../../../../models/usuario';
import { AlertService } from '../../../../services/alert.service';
import { AuthService } from '../../../../services/auth.service';
import { RolService } from '../../../../services/rol.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-asignar-rol-usuario',
  templateUrl: './asignar-rol-usuario.component.html',
  styleUrl: './asignar-rol-usuario.component.css',
  standalone: true,
  imports: [CheckboxUsuarioRolComponent, CommonModule, MatDatepickerModule, MatTableModule, MatPaginatorModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatCheckboxModule, MatIconModule, MatDialogModule]

})
export class AsignarRolUsuarioComponent implements OnInit {

  rolesInsertar: Role[] = [];
  rolesEliminar: Role[] = [];
  modulos: Modulo[] = [];
  usuarioSeleccionado!: Usuario;

  constructor(private rolService: RolService,
    private usuarioService: UsuarioService,
    public authService: AuthService,
    private alertService: AlertService,
    private router: Router) { }

  ngOnInit() {
    this.usuarioSeleccionado = this.usuarioService.usuario;
    this.rolService.modulos().subscribe(result => {
      this.modulos = result
    });
  }

  allChecks(modulo: Modulo) {
    const index = findIndex(this.modulos, (m) => m.id == modulo.id)
    this.modulos[index] = modulo
  }

  onSubmitForm() {
    this.modulos.forEach(m => {
      m.roles.forEach(r => {
        const index = findIndex(this.usuarioSeleccionado.roles, (rr) => rr.id == r.id)
        if (index != -1) {
          if (!r.activated) {
            console.log("aqui elimina:", r.descripcion);
            this.rolesEliminar.push(r);
          }
          if (r.activated) {
            console.log("aqui elimina:", r.descripcion);
            this.rolesInsertar.push(r);
          }
        } else {
          if (r.activated) {
            console.log("aqui inserta el rol:", r.descripcion);
            this.rolesInsertar.push(r)
          }
        }
      })
    })
    if (this.rolesEliminar.length) {
      this.usuarioSeleccionado.roles = [];
      this.usuarioSeleccionado.roles = this.rolesEliminar;
      this.usuarioService.deleteRolUsuario(this.usuarioSeleccionado, this.usuarioSeleccionado.id).subscribe(resp => {
        this.alertService.success(resp.mensaje, "Asignar roles a usaurio");
      })
    }

    if (this.rolesInsertar.length) {
      this.usuarioSeleccionado.roles = [];
      this.usuarioSeleccionado.roles = this.rolesInsertar;
      this.usuarioService.updateRolUsuario(this.usuarioSeleccionado, this.usuarioSeleccionado.id).subscribe(resp => {
        this.alertService.success(resp.mensaje, "Asignar roles a usaurio");
      })
    }
    this.router.navigate(['/usuarios']);



  }

}


