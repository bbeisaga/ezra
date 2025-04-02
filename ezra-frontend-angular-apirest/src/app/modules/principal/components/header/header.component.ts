import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Usuario } from '../../../../models/usuario';
import { AlertService } from '../../../../services/alert.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [ './header.component.css']
})
export class HeaderComponent implements OnInit {
  title: string = 'Sistema de pedido EZRA'
  isAutenticado: boolean = false;
  usuario!: Usuario;
  @Output()
  clickMenuEvent = new EventEmitter();

  constructor(private authService: AuthService,
              private router: Router,
            private alertService: AlertService) { }

  ngOnInit(): void {
    this.isAutenticado = this.authService.isAuthenticated();
    if(this.isAutenticado){
      this.usuario = this.authService.usuario;
    }
  }


  logout(): void {
    let username = this.authService.usuario.username;
    this.authService.logout();
    this.alertService.success(`Hola ${username}, has cerrado sesión con éxito!`,'Cerrar sesión');
    //swal.fire('Logout', `Hola ${username}, has cerrado sesión con éxito!`, 'success');
    this.router.navigate(['/login']);
  }

  sideNavToggle(): void {
    this.clickMenuEvent.emit();
  }

}
