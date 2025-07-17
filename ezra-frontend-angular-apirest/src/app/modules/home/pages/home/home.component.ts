import { Component, Inject } from '@angular/core';
import { LoadJavascriptService } from '../../../../services/load-javascript.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private loadJavascriptService: LoadJavascriptService) {

    loadJavascriptService.load(['easing.min', 'waypoints.min', 'counterup.min', 'isotope.pkgd.min', 'owl.carousel.min', 'lightbox.min', 'main']);
  }
}