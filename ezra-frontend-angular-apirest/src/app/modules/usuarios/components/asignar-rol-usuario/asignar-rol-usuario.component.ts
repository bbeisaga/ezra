import { Component, OnInit } from '@angular/core';
import { Role } from '../../../../models/role';
import { Modulo } from '../../../../models/modulo';
import { RolService } from '../../../../services/rol.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { Usuario } from '../../../../models/usuario';
import { find, findIndex } from 'lodash';
import { AlertService } from '../../../../services/alert.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-asignar-rol-usuario',
  templateUrl: './asignar-rol-usuario.component.html',
  styleUrl: './asignar-rol-usuario.component.css'
})
export class AsignarRolUsuarioComponent implements OnInit {

  rolesInsertar: Role[] = [];
  rolesEliminar: Role[] = [];
  modulos: Modulo[] = [];
  usuarioSeleccionado!: Usuario;

  constructor(private rolService: RolService,
    private usuarioService: UsuarioService,
    public authService: AuthService,
    private alertService:AlertService,
  private router:Router) { }

  ngOnInit() {
    this.usuarioSeleccionado = this.usuarioService.usuario;
    this.rolService.modulos().subscribe(result => {
      this.modulos = result
    });
  }

  allChecks(modulo: Modulo){
    const index = findIndex(this.modulos, (m)=> m.id == modulo.id)
    this.modulos[index] = modulo
  }

  onSubmitForm(){
    this.modulos.forEach(m => {
      m.roles.forEach(r =>{
        const index = findIndex(this.usuarioSeleccionado.roles, (rr)=> rr.id == r.id)
        if(index!=-1){
          if(!r.activated){
            console.log("aqui elimina:",r.descripcion);
            this.rolesEliminar.push(r);
          }
          if(r.activated){
            console.log("aqui elimina:",r.descripcion);
            this.rolesInsertar.push(r);
          }
        } else {
          if(r.activated){
            console.log("aqui inserta el rol:",r.descripcion);
            this.rolesInsertar.push(r)
          }
        }
      })
    })
    if(this.rolesEliminar.length){
      this.usuarioSeleccionado.roles=[];
      this.usuarioSeleccionado.roles = this.rolesEliminar;
      this.usuarioService.deleteRolUsuario(this.usuarioSeleccionado, this.usuarioSeleccionado.id).subscribe(resp => {
        this.alertService.success(resp.mensaje,"Asignar roles a usaurio");
      })
    }

    if(this.rolesInsertar.length){
      this.usuarioSeleccionado.roles=[];
      this.usuarioSeleccionado.roles = this.rolesInsertar;
      this.usuarioService.updateRolUsuario(this.usuarioSeleccionado, this.usuarioSeleccionado.id).subscribe(resp => {
        this.alertService.success(resp.mensaje,"Asignar roles a usaurio");
      })
    }
    this.router.navigate(['/usuarios']);



  }

}


