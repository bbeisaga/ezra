import { Producto } from './producto';

export class ItemPedido {
  producto!: Producto;
  cantidad: number=1;
 // costo!: number;
  importe!: number;
  descripcion!: string;

  /*
  public calcularImporteVentaCliente(): number {
    return this.cantidad * this.importe;
  }

   public calcularCosto(): number {
    return this.cantidad * this.importe;
  } */
}
