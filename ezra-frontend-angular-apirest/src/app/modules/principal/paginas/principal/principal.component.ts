import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {

  sidenavOpened = true;


  constructor(public authService: AuthService) { }

  ngOnInit() {
  }

  sideNavToggle(sidenavLeft : any, val : boolean): void {

    sidenavLeft.opened = val;
    //this.sidenavOpened = !this.sidenavOpened;
  }

}
