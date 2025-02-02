import { Caja } from "./caja";
import { MovimientoVenta } from "./movimiento-venta";
import { Usuario } from "./usuario";


export class CajaUsuario {
	id!: number;
	fechaApertura!: string;
	fechaCierre!: string;
	ingresoEsperado!:number;
	ingresoPorConteo!:number;
	egresoEsperado!:number;
	saldoCaja:number = 0;
  saldoPorConteo!:number;
  activa:boolean=false;
	caja!: Caja;
	usuario!: Usuario;
	movimientosVenta: MovimientoVenta[]=[];
}
