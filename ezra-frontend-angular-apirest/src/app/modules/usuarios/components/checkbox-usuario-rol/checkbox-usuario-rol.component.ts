import { Component, Input, OnInit } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { RolService } from '../../../../services/rol.service';
import { UsuarioService } from '../../../../services/usuario.service';
import { Modulo } from '../../../../models/modulo';
import { Usuario } from '../../../../models/usuario';
import { Role } from '../../../../models/role';
import { findIndex } from 'lodash';

@Component({
  selector: 'checkbox-usuario-rol',
  templateUrl: './checkbox-usuario-rol.component.html',
  styleUrl: './checkbox-usuario-rol.component.css'
})
export class CheckboxUsuarioRolComponent implements OnInit {

  @Input() modulo!: Modulo;
  @Input() usuarioSeleccionado!: Usuario;

  //@Input() activatedActions: CheckboxActionItem[] = [];
  //@Output() accumulatePermits: EventEmitter<TsgModule> = new EventEmitter();
  //@Input() validate: boolean;

  roles: string[] = [];
  modulos: Modulo[] = [];
  //usuario!: Usuario;
  checkboxRoles: CheckboxRoles[]=[]
  checkboxModul!: CheckboxModul;


/*   task: Task = {
    name: 'Pedidos',
    completed: false,
    color: 'primary',
    subtasks: [
      { name: 'Listar', completed: false, color: 'primary' },
      { name: 'Insertar', completed: false, color: 'accent' },
      { name: 'Editar', completed: false, color: 'accent' },
      { name: 'Borrar', completed: false, color: 'warn' },
    ],
  }; */

  allComplete: boolean = false;

  constructor(private rolService: RolService,
    private usuarioService: UsuarioService) { }

  ngOnInit() {
  console.log("modulo",this.modulo);
//  console.log("usuarioSeleccionado",this.usuario);

    this.modulo.roles.forEach((r) => {
          this.checkboxRoles.push({
            id: r.id,
            nombre:r.nombre,
            descripcion:r.descripcion,
            activated:this.permissionIsActivated(r.id)
          })
      //this.updateAllComplete()
    })
    this.checkboxModul = {
      id: this.modulo.id,
      nombre: this.modulo.nombre,
      activated:false,
      checkboxRoles : this.checkboxRoles
    }

    this.updateAllComplete();
  }


/*   updateAllComplete2() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }
 */
  updateAllComplete() {
    this.allComplete = this.checkboxModul.checkboxRoles != null && this.checkboxModul.checkboxRoles.every(t => t.activated);
  }

  someComplete(): boolean {
    if (this.checkboxModul.checkboxRoles == null) {
      return false;
    }
    return this.checkboxModul.checkboxRoles.filter(t => t.activated).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.checkboxModul.checkboxRoles == null) {
      return;
    }
    this.checkboxModul.checkboxRoles.forEach(t => (t.activated = completed));
  }


  permissionIsActivated(rolId: number): boolean {

    const index = findIndex(this.usuarioSeleccionado.roles, (ap) => ap.id == rolId);
    return index != -1;
  }
}

/* export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
} */


export interface CheckboxModul {
  id: number;
  nombre: string;
  activated: boolean;
  checkboxRoles:CheckboxRoles[]
}

export interface CheckboxRoles {
  id: number;
  nombre: string;
  descripcion: string;
  activated: boolean;
}
