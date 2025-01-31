import { CajaUsuario } from "./caja-usuario";
import { Pedido } from "./pedido";
import { TipoMovimiento } from "./tipo-movimiento";
import { TipoPago } from "./tipo-pago";


export class Movimiento {
  id!:number;
  pedido!: Pedido;
	cajaUsuario!: CajaUsuario;
  tipoPago!: TipoPago;
  tipoMovimiento!: TipoMovimiento;
	//createAt!: string;
	//pagoDinero!:number;
  //vueltoDinero!:number;
  ingresoDinero!:number;
  egresoDinero!:number;
  saldoDinero!:number;
}
