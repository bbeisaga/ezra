import swal from 'sweetalert2';
import { Component, OnInit } from '@angular/core';
import { Movimiento } from '../../../models/movimiento';
import { CajaService } from '../../../services/caja.service';
import { MovimientoService } from '../../../services/movimiento.service';
import { TipoPago } from '../../../models/tipo-pago';
import { Pedido } from '../../../models/pedido';
import { PedidoService } from '../../../services/pedido.service';
import { CajaUsuario } from '../../../models/caja-usuario';
import { AuthService } from '../../../services/auth.service';
import { Usuario } from '../../../models/usuario';
import { TipoMovimiento } from '../../../models/tipo-movimiento';
import { find } from 'lodash';
import { Router } from '@angular/router';
import { concat } from 'rxjs';

@Component({
  selector: 'app-movimiento-pedido',
  templateUrl: './movimiento-pedido.component.html',
  styleUrl: './movimiento-pedido.component.css'
})
export class MovimientoPedidoComponent implements OnInit {
  titulo: string = 'Movimiento de pago'
  movimiento = new Movimiento();
  cajaUsuario?: CajaUsuario;
  pedido! : Pedido;
  tipoPagos: TipoPago[]=[];
  tipoMovimientos: TipoMovimiento[]=[];
  isAutenticado!: boolean;
  user!: Usuario;


  constructor(private cajasService: CajaService,
              private movimientoService: MovimientoService,
              private pedidoService : PedidoService,
              private authService: AuthService,
              private router: Router
  ){

  }


  ngOnInit(): void {
    this.isAutenticado = this.authService.isAuthenticated();
    if(this.isAutenticado){
      this.user = this.authService.usuario;
    }
    //trae pedidos
    this.pedido = this.pedidoService.pedido;
    console.log("pedido", this.pedido);

    //trae la caja activa por usuario
    this.cajasService.getCajaUsuarioByUserName(this.user).subscribe(
      res => {
        console.log("getCajaUsuarioByUserName...", res)
        if(res !== null && res.activa){
          this.movimiento.cajaUsuario = res
        } else {
          swal.fire('', `Debe aperturar caja`, 'info');
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

    console.log("movimiento", this.movimiento);
  }

  findTipoMovimiento(id: number): TipoMovimiento {
    return find(this.tipoMovimientos,{'id': id})!
  }

  setMovimientoDinero():void{
    //console.log(111);
    //debugger;
    let newSaldo = (this.pedido.saldoPedido - this.movimiento.ingresoDinero)
    this.movimiento.ingresoDinero = this.movimiento.ingresoDinero
    if(newSaldo >= 0) {
      this.movimiento.egresoDinero = 0
      //this.movimiento.ingresoDinero = this.movimiento.ingresoDinero
      this.movimiento.saldoDinero = newSaldo;
    }
    if(newSaldo < 0) {
      this.movimiento.egresoDinero = newSaldo
      //this.movimiento.ingresoDinero = this.pedido.saldoPedido;
      //this.movimiento.saldoDinero = (this.pedido.saldoPedido - this.movimiento.ingresoDinero)
      this.movimiento.saldoDinero = 0

    }

    //console.log("this.movimiento", this.movimiento);
  }

  onSubmitForm(){

    this.movimiento.pedido = this.pedido
    this.movimiento.pedido.items=[];
    this.movimiento.pedido.movimientos=[];
    console.log("onSubmitForm...", this.movimiento);


    this.movimientoService.createMovimiento(this.movimiento).subscribe(
      resp => {
          swal.fire(this.titulo, `Movimiento ${resp.cajaUsuario.id}, creado con éxito!`, 'success');
          this.router.navigate(['/pr']);
      })

/*     concat(this.movimientoService.createMovimiento(this.movimiento), this.pedidoService.update(this.movimiento.pedido))
    .subscribe(
      resp => {
          swal.fire(this.titulo, `Movimiento registrado con éxito!`, 'success');
          this.router.navigate(['/pr']);
      }
    ) */


  }


}



