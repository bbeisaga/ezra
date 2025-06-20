import { Producto } from './producto';

export class ItemPedido {
  producto!: Producto;
  cantidad: number=0;
 // costo!: number;
  importe: number=0;
  descripcion!: string;
  imagen:string = 'no-imagen.jpg';

  /*
  public calcularImporteVentaCliente(): number {
    return this.cantidad * this.importe;
  }

   public calcularCosto(): number {
    return this.cantidad * this.importe;
  } */
}
