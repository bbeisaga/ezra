import { CajaUsuario } from "./caja-usuario";
import { Pedido } from "./pedido";
import { TipoMovimiento } from "./tipo-movimiento";
import { TipoPago } from "./tipo-pago";


export class Movimiento {
  id!:number;
  pedido!: Pedido;
	cajaUsuario!: CajaUsuario;
  tipoMovimiento!: TipoMovimiento;
  tipoPago!: TipoPago;
	createAt!: string;
	monto!:number;
}
