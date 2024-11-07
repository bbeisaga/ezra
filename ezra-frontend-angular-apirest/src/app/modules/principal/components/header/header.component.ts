import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Usuario } from '../../../../models/usuario';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  title: string = 'App Angular'
  isAutenticado: boolean = false;
  usuario?: Usuario;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.isAutenticado = this.authService.isAuthenticated();
    if(this.isAutenticado){
      this.usuario = this.authService.usuario;
    }
  }


  logout(): void {
    let username = this.authService.usuario.username;
    this.authService.logout();
    swal.fire('Logout', `Hola ${username}, has cerrado sesión con éxito!`, 'success');
    this.router.navigate(['/login']);
  }
}
