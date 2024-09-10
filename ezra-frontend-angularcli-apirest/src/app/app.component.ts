import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
/*   title = 'Bienvenido a Angular';

  curso: string = "Angular con Spring 5";

  profesor: string = "Andrés Guzmán" */

  constructor(private authService: AuthService) { }

}
