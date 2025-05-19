import { Pedido } from './pedido';
import { TipoDocumento } from './tipo-documento';

export class Cliente {
  id!: number;
  nomApellRz!:string;
  direccion!:string;
  createAt!: string;
  tipoDocumento!: TipoDocumento;
  numeroDocumento!: string;
  celular!: string;
  pedidos: Array<Pedido> = [];
}
