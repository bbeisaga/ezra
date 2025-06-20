import { Component, EventEmitter, Output } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { AlertService } from '../../../services/alert.service';
import { Usuario } from '../../../models/usuario';

@Component({
  selector: 'menu-app',
  templateUrl: './menu-app.component.html',
  styleUrl: './menu-app.component.css'
})
export class MenuAppComponent {

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
    if (this.isAutenticado) {
      this.usuario = this.authService.usuario;
    }
  }

  logout(): void {
    let username = this.authService.usuario.username;
    this.authService.logout();
    this.alertService.success(`Hola ${username}, has cerrado sesión con éxito!`, 'Cerrar sesión');
    //swal.fire('Logout', `Hola ${username}, has cerrado sesión con éxito!`, 'success');
    this.router.navigate(['/tienda']);
  }

}
