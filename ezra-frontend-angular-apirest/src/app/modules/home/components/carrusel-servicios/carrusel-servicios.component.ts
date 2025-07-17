import { Component, ElementRef } from '@angular/core';


@Component({
  selector: 'app-carrusel-servicios',
  templateUrl: './carrusel-servicios.component.html',
  styleUrl: './carrusel-servicios.component.css'
})
export class CarruselServiciosComponent {

  constructor(private elementRef: ElementRef) { }

  ngOnInit(): void {
    //this.loadScript(['carrusels']);

  }

  ngAfterViewInit(): void {
    // Asegúrate de que el script se haya cargado completamente
    // antes de intentar acceder a sus funcionalidades.
    // Puedes usar setTimeout o eventos de carga del script.
  }

  loadScript(archivos: string[]) {
    for (let archivo of archivos) {
      let script = document.createElement('script');
      script.src = "../../../../../assets/js/" + archivo + ".js";
      script.type = "text/javascript";
      script.async = true;
      console.log(script.src)
      document.body.appendChild(script);
      //this.elementRef.nativeElement.appendChild(script);

    }
  }
}
