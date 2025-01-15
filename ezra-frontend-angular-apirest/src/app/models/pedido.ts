import { Cliente } from './cliente';
import { EstadoPedido } from './estado-pedido';
import { ItemPedido } from './item-pedido';
import { Movimiento } from './movimiento';

export class Pedido {
  id!:number;
  //descripcion!: string;
  observacion!: string;
  items:Array<ItemPedido> = [];
  movimientos:Array<Movimiento> = [];
  cliente?:Cliente;
  estadoPedido?:EstadoPedido;
  pago:number=0;
  apagar:number=0;
  saldo!:number;
  total!:number;
  createAt!:string;
  entregadoEn!:string;
  aceptado:boolean = true;
  vencido:boolean = false;
  pagado:boolean = false;


  calcularGranTotal(): number {
    this.total = 0;
    this.items.forEach((item: ItemPedido) => {
      this.total += item.calcularImporte();
    });
    return this.total;
  }

  calcularGranSaldo(): number {
    return this.calcularGranTotal() - this.pago;
  }

}
