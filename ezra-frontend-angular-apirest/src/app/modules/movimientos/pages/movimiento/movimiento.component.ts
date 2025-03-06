import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Movimiento } from '../../../../models/movimiento';
import { CajaService } from '../../../../services/caja.service';
import { MovimientoService } from '../../../../services/movimiento.service';
import { TipoPago } from '../../../../models/tipo-pago';
import { Pedido } from '../../../../models/pedido';
import { PedidoService } from '../../../../services/pedido.service';
import { CajaUsuario } from '../../../../models/caja-usuario';
import { AuthService } from '../../../../services/auth.service';
import { TipoMovimiento } from '../../../../models/tipo-movimiento';
import { find } from 'lodash';
import { Router } from '@angular/router';
import moment from 'moment';
import { COLOR_CAJA_USUARIO, ESTADO_CAJA_USUARIO } from '../../../../constants/caja-usuario.constants';
import { AlertService } from '../../../../services/alert.service';

@Component({
  selector: 'app-movimiento',
  templateUrl: './movimiento.component.html',
  styleUrl: './movimiento.component.css'
})
export class MovimientoComponent implements OnInit {
  titulo: string = 'Movimiento de pago'
  movimiento = new Movimiento();
  cajaUsuario!: CajaUsuario;
  pedido! : Pedido;
  tipoPagos: TipoPago[]=[];
  tipoMovimientos: TipoMovimiento[]=[];
  isAutenticado!: boolean;
  //user!: Usuario;
  username!:string;
  estadoCajaUsuarioMap = ESTADO_CAJA_USUARIO;


  constructor(private cajasService: CajaService,
              private movimientoService: MovimientoService,
              private pedidoService : PedidoService,
              private authService: AuthService,
              private alertService:AlertService,
              private router: Router
  ){

  }


  ngOnInit(): void {
    //this.isAutenticado = this.authService.isAuthenticated();
    if(this.authService.isAuthenticated()){
      this.username = this.authService.usuario.username;
    }
    //trae pedidos
    this.pedido = {...this.pedidoService.pedido};
    console.log("pedido", this.pedido);
    this.cajasService.getCajaUsuarioByUserName(this.username).subscribe(
      res => {
        console.log("getCajaUsuarioByUserName...", res)
        if(res !== null && res.activa){
          this.cajaUsuario = res
          this.cajaUsuario.fechaApertura = moment(this.cajaUsuario.fechaApertura).format('DD/MM/YYYY HH:mm:ss');
          this.cajaUsuario.fechaActualizacion = moment(this.cajaUsuario.fechaActualizacion).format('DD/MM/YYYY HH:mm:ss');
          this.cajaUsuario.color = COLOR_CAJA_USUARIO[('' + res.activa) as keyof typeof COLOR_CAJA_USUARIO ];


        } else {
          this.alertService.info(`Debe aperturar caja`,"Caja")
          //swal.fire('', `Debe aperturar caja`, 'info');
          this.router.navigate(['/pr']);
        }
      }
    )
    // trae tipo de pagos
    this.movimientoService.getAllTipoPagos().subscribe(
      response => this.tipoPagos = response
    )

    //trae tipo de movimientos
    this.movimientoService.getAllTipoMovimientos().subscribe(
      response => {
        this.tipoMovimientos = response
        this.movimiento.tipoMovimiento = this.findTipoMovimiento(1);
      })
    //console.log()

    //console.log("movimiento", this.movimiento);
  }



  findTipoMovimiento(id: number): TipoMovimiento {
    return find(this.tipoMovimientos,{'id': id})!
  }

  setMovimientoDinero():void{
    if(this.pedido.tipoPedido.nombre=="VENTA AL CLIENTE"){
     // let newSaldoBruto = (this.pedido.saldoBrutoPedido - this.movimiento.ingresoDinero)
      let newSaldo = (this.pedido.saldoPedido - this.movimiento.ingresoDinero)
/*
      if(newSaldoBruto >= 0) {
        this.movimiento.egresoDinero = 0
        this.movimiento.saldoBrutoDinero = newSaldoBruto;
      }
      if(newSaldoBruto < 0) {
        this.movimiento.egresoDinero = newSaldoBruto
        this.movimiento.saldoBrutoDinero = 0
      } */
      if(newSaldo >= 0) {
        this.movimiento.egresoDinero = 0
        this.movimiento.saldoDinero = newSaldo;
      }
      if(newSaldo < 0) {
        this.movimiento.egresoDinero = newSaldo
        this.movimiento.saldoDinero = 0
      }
    }
    if(this.pedido.tipoPedido.nombre=="COMPRA O ADQUISICION"){
      //let newSaldoBruto = (this.pedido.saldoBrutoPedido - this.movimiento.egresoDinero)
      let newSaldo = (this.pedido.saldoPedido - this.movimiento.egresoDinero)

/*       if(newSaldoBruto >= 0) {
        this.movimiento.ingresoDinero = 0
        this.movimiento.saldoBrutoDinero = newSaldoBruto;
      }
      if(newSaldoBruto < 0) {
        this.movimiento.ingresoDinero = newSaldoBruto
        this.movimiento.saldoBrutoDinero = 0
      } */
      if(newSaldo >= 0) {
        this.movimiento.ingresoDinero = 0
        this.movimiento.saldoDinero = newSaldo;
      }
      if(newSaldo < 0) {
        this.movimiento.ingresoDinero = newSaldo
        this.movimiento.saldoDinero = 0
      }
    }
  }

  onSubmitForm(){
    this.movimiento.pedido = this.pedido
    this.movimiento.pedido.createAt="";
    this.movimiento.pedido.entregadoEn="";
    this.movimiento.pedido.adquiridoEn="";
    this.movimiento.pedido.items=[];
    this.movimiento.pedido.movimientos=[];

    this.cajaUsuario.fechaApertura="";
    this.cajaUsuario.fechaActualizacion="";
    this.cajaUsuario.movimientos=[]

    this.movimiento.cajaUsuario = {...this.cajaUsuario}
    console.log("onSubmitForm...", this.movimiento);
    this.movimientoService.createMovimiento(this.movimiento).subscribe(
      resp => {
        this.alertService.success(`Movimiento ${resp.cajaUsuario.id}, creado con éxito!`, this.titulo)
          //swal.fire(this.titulo, `Movimiento ${resp.cajaUsuario.id}, creado con éxito!`, 'success');
          this.router.navigate(['/pr']);
      })
  }


}



