import { Cliente } from './cliente';
import { ItemPedido } from './item-pedido';

export class Pedido {
  id!: number;
  descripcion!: string;
  observacion!: string;
  items: Array<ItemPedido> = [];
  cliente?: Cliente;
  acuenta: number=0;
  saldo!: number;
  total!: number;
  createAt!: string;

  calcularGranTotal(): number {
    this.total = 0;
    this.items.forEach((item: ItemPedido) => {
      this.total += item.calcularImporte();
    });
    return this.total;
  }

  calcularGranSaldo(): number {
    return this.calcularGranTotal() - this.acuenta;
  }

}
