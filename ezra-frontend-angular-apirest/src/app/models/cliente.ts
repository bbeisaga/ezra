import { Pedido } from './pedido';
import { TipoDocumento } from './tipo-documento';

export class Cliente {
  id!: number;
  nombres!: string;
  apellidos!: string;
  createAt!: string;
/*   email!: string;
  foto!: string; */
  tipoDocumento!: TipoDocumento;
  numeroDocumento!: string;
  celular!: string;
  pedidos: Array<Pedido> = [];
}
