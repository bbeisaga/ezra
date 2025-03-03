import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MovimientoCaja } from '../../../../models/movimiento-caja';
import { CajaUsuario } from '../../../../models/caja-usuario';
import { TipoMovimiento } from '../../../../models/tipo-movimiento';
import { Usuario } from '../../../../models/usuario';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { CajaService } from '../../../../services/caja.service';
import { MovimientoService } from '../../../../services/movimiento.service';
import swal from 'sweetalert2';
import { find, keys } from 'lodash';
import moment from 'moment';
import { COLOR_CAJA_USUARIO, ESTADO_CAJA_USUARIO } from '../../../../constants/caja-usuario.constants';

@Component({
  selector: 'app-movimiento-caja',
  templateUrl: './movimiento-caja.component.html',
  styleUrl: './movimiento-caja.component.css'
})
export class MovimientoCajaComponent implements OnInit, AfterViewInit {
  titulo: string = 'Movimiento de caja'
  movimiento = new MovimientoCaja();
  cajaUsuario!: CajaUsuario;
  tipoMovimientos: TipoMovimiento[]=[];
  tipoMovIngresos:TipoMovimiento[]=[];
  tipoMovEgresos:TipoMovimiento[]=[];
  isAutenticado!: boolean;
  //user!: Usuario;
  username!:string;
  iMovimiento:string="I";
  estadoCajaUsuarioMap = ESTADO_CAJA_USUARIO;

  constructor(private cajasService: CajaService,
              private movimientoService: MovimientoService,
              private authService: AuthService,
              private router: Router
  ){

  }


  ngOnInit(): void {
    //this.isAutenticado = this.authService.isAuthenticated();
    if(this.authService.isAuthenticated()){
      this.username = this.authService.usuario.username;
    }

    this.cajasService.getCajaUsuarioByUserName(this.username).subscribe(
      res => {
        //console.log("getCajaUsuarioByUserName...", res)
        if(res !== null && res.activa){
          this.cajaUsuario = res
          this.cajaUsuario.fechaApertura = moment(this.cajaUsuario.fechaApertura).format('DD/MM/YYYY HH:mm:ss');
          this.cajaUsuario.fechaActualizacion = moment(this.cajaUsuario.fechaActualizacion).format('DD/MM/YYYY HH:mm:ss');
          this.cajaUsuario.color = COLOR_CAJA_USUARIO[('' + res.activa) as keyof typeof COLOR_CAJA_USUARIO ];
          //this.colores[('' + res.activa) as keyof typeof this.colores ];
          //        console.log("getCajaUsuarioByUserName...", res)

        } else {
          swal.fire('', `Debe aperturar caja`, 'info');
          this.router.navigate(['/pr']);
        }
      }
    )

    this.movimientoService.getAllTipoMovimientos().subscribe(
      response => {
         this.tipoMovIngresos = response.filter( f => f.tipo == "I")
         this.tipoMovEgresos = response.filter( f => f.tipo == "E")

      })

  }
  ngAfterViewInit(): void {
      this.changeTipoMovimiento(this.iMovimiento);
  }

  changeTipoMovimiento(tipo:string){
    this.tipoMovimientos=[]
    if(tipo=="I") {
      this.tipoMovimientos = this.tipoMovIngresos
      this.movimiento.egresoDinero=0; }
    if(tipo=="E") {
      this.tipoMovimientos = this.tipoMovEgresos
      this.movimiento.ingresoDinero=0; }
  }

  findTipoMovimiento(id: number): TipoMovimiento {
    return find(this.tipoMovimientos,{'id': id})!
  }

  onSubmitForm(){
    this.cajaUsuario.movimientos=[]
    this.cajaUsuario.movimientosCaja=[]
    this.cajaUsuario.fechaApertura="";
    this.cajaUsuario.fechaActualizacion="";
    this.movimiento.cajaUsuario = {...this.cajaUsuario}
    console.log("onSubmitForm...", this.movimiento);
    this.movimientoService.createMovimientoCaja(this.movimiento).subscribe(
      resp => {
          swal.fire(this.titulo, `Movimiento ${resp.cajaUsuario.id}, creado con éxito!`, 'success');
          this.router.navigate(['/pr']);
      })
  }

}
