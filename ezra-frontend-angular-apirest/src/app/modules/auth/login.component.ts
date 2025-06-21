import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario';
import swal from 'sweetalert2';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']

})
export class LoginComponent implements OnInit {

  titulo: string = 'Identifícate';
  usuario: Usuario;

  constructor(private authService: AuthService,
              private router: Router,
              private alertService: AlertService) {
    this.usuario = new Usuario();
  }

  ngOnInit() {
     if (this.authService.isAuthenticated()) {
      this.alertService.success(`Hola ${this.authService.usuario.username} ya estás autenticado!`, 'Autenticación')
      //swal.fire('Login', `Hola ${this.authService.usuario.username} ya estás autenticado!`, 'info');
      this.router.navigate(['']);
    }
  }

  login(): void {
     //console.log(this.usuario);
    if (this.usuario.username == null || this.usuario.password == null) {
      //swal.fire('Error Login', 'Username o password vacías!', 'error');
      this.alertService.info(`Usuario o clave vacios!`, 'Autenticación')

      return;
    }

    this.authService.login(this.usuario).subscribe(response => {
      console.log(response);
      this.authService.guardarUsuario(response.token);
      this.authService.guardarToken(response.token);
      let usuario = this.authService.usuario;
      this.router.navigate(['/']);
      //this.alertService.success(`Hola ${usuario.username}, has iniciado sesión con éxito!`, 'Autenticación')
      //swal.fire('Login', `Hola ${usuario.username}, has iniciado sesión con éxito!`, 'success');
    }, err => {
      //if (err.status == 400) {
        this.alertService.error('Usuario o clave incorrectas!', 'Autenticación')
        //swal.fire('Error Login', 'Usuario o clave incorrectas!', 'error');
     // }
    }
    );
  }

}
