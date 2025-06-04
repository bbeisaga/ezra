import { Categoria } from "./categoria"
import { Color } from "./color"
import { Empaque } from "./empaque"
import { Material } from "./material"
import { Origen } from "./origen"
import { Uso } from "./uso"
import { GenericosDeProducto } from './genericos-de-producto';
import { GaleriaProducto } from "./galeria-producto"

export class Producto {
  id!: number;
  nombre!: string;
  descripcion!: string;
  medidas?:string
  peso?:string
  imagen!: string;
  // galeriaProducto: GaleriaProducto[]=[]
  unbralPocaCantidad!: number;
  unbralAgotadaCantidad!: number;
  cantidadStock!: number;
  minCantidadPedido!: number;
  maxCantidadPedido!: number;
  gruposDe!: number;
  costoUnitario!: number;
  costoUnitarioEmpaque!: number;
  precioBruto!: number;
  precioBrutoRebajado!: number;
  precioNeto!: number;
  precioNetoRabajado!: number;
  //createAt!:string
  fechaPrecioRebajadoDesde!:string;
  fechaPrecioRebajadoHasta!:string;
  //productosRelacionados!:string; //ventas dirigidas(prod sustituto por la calidad, mas caros)
  //productosPromocion!:string;
	visibleEnTienda!: boolean;
	activo!: boolean;
  color?:Color;
  material?: Material;
  categoria!:Categoria;
  uso!: Uso;
}


//
