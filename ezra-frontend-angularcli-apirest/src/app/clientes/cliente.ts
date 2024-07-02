import { Region } from './region';
import { Pedido } from '../pedidos/models/pedido';

export class Cliente {
  id: number;
  nombre: string;
  apellido: string;
  createAt: string;
  email: string;
  foto: string;
  region: Region;
  pedidos: Array<Pedido> = [];
}
