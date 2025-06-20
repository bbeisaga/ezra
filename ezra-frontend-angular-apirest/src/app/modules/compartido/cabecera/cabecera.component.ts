import { Component, EventEmitter, inject, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'cabecera',
  templateUrl: './cabecera.component.html',
  styleUrl: './cabecera.component.css'
})
export class CabeceraComponent implements OnInit {

  @Output()
  clickMenuEvent = new EventEmitter<any>();

  private activateRoute = inject(ActivatedRoute)
  public authService = inject(AuthService)

  modoTienda!: boolean;

  constructor() { }

  sideNavToggle(): void {
    this.clickMenuEvent.emit();
  }

  ngOnInit(): void {
    this.activateRoute.queryParams.subscribe(qp => {
      console.log(qp['modoTienda']);
      console.log(!qp['modoTienda']);
      console.log(!!qp['modoTienda']);
      this.modoTienda = !!qp['modoTienda']
    });
  }

}
