import { CajaUsuario } from "./caja-usuario";
import { Pedido } from "./pedido";
import { TipoMovimiento } from "./tipo-movimiento";
import { TipoPago } from "./tipo-pago";


export class MovimientoCaja {
  id!:number;
	cajaUsuario!: CajaUsuario;
  tipoMovimiento!: TipoMovimiento;
  ingresoDinero:number=0;
  egresoDinero:number=0;
  descripcion!:string;
}
