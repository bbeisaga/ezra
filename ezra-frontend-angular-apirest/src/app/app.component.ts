import { Component } from '@angular/core';
import { ZonaHorariaDefectoService } from './services/zona-horaria-defecto.service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
  imports: [RouterOutlet]
})
export class AppComponent {
/*   title = 'ezra-frontend-angular-apirest';
 */  title = 'Ezra';
  constructor(private zonaHorariaDefectoService: ZonaHorariaDefectoService){

  }
}
