import { Cliente } from './cliente';
import { EstadoPedido } from './estado-pedido';
import { ItemPedido } from './item-pedido';
import { MovimientoVenta } from './movimiento-venta';
import { TipoPedido } from './tipo-pedido';

export class Pedido {
  id!:number;
  //descripcion!: string;
  observacion!: string;
  items:Array<ItemPedido> = [];
  movimientosVenta:Array<MovimientoVenta> = [];
  cliente?:Cliente;
  estadoPedido!:EstadoPedido;
  tipoPedido!:TipoPedido;
  costoTotal:number=0;
  precioBrutoTotal!:number;
  precioNetoTotal:number=0;
  pagoBrutoTotal!:number;
  pagoNetoTotal!:number;
  saldoPedido!:number;
  //total!:number;
  createAt!:string;
  entregadoEn!:string;
  adquiridoEn!:string;
  aceptado:boolean = true;
  vencido:boolean = false;
  pagado:boolean = false;


/*    calcularGranTotal(): number {
    this.precioNetoTotal = 0;
    this.items.forEach((item: ItemPedido) => {
      this.precioNetoTotal += item.calcularImporte();
    });
    return this.precioNetoTotal;
  } */
/*
  calcularGranSaldo(): number {
    return this.calcularGranTotal() - this.pago;
  } */

}
