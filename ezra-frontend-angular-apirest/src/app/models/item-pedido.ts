import { Producto } from './producto';

export class ItemPedido {
  producto!: Producto;
  cantidad: number = 1;
  importe!: number;
  descripcion!: string;

  public calcularImporte(): number {
    return this.cantidad * this.producto.precioNeto;
  }
}
