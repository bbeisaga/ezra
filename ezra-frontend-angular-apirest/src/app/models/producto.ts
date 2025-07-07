import { Categoria } from "./categoria"
import { Color } from "./color"
import { Empaque } from "./empaque"
import { Material } from "./material"
import { Origen } from "./origen"
import { Uso } from "./uso"
import { GenericosDeProducto } from './genericos-de-producto';
import { GaleriaProducto } from "./galeria-producto"
import { EstadoProducto } from "./estado-producto"

export class Producto {
  id!: number;
  codigo!:string;
  nombre!: string;
  descripcion!: string;
  medidas?:string
  peso?:string
  // galeriaProducto: GaleriaProducto[]=[]
  umbralPocaCantidad: number = 1;
  umbralCantidadAgotada: number= 0;
  cantidadVendidos:number=0;
  cantidadStock: number = 0; // esto se actualiza al comprar
  minCantidadPedido: number = 1;
  maxCantidadPedido: number = 100000;
  gruposDe: number=1;
  costoUnitario: number=0;// esto se actualiza al comprar
  costoPersonalizacion: number=0;
  impuestoIgv:number=18;
  margenGanancia:number=0;
  //costoUnitarioEmpaque: number=0;
  //precioBruto: number = 3;
  precioNeto: number=0;
  //precioBrutoRebajado: number=2;
  //precioNetoRabajado: number=3;
  //createAt!:string
  //fechaPrecioRebajadoDesde!:string;
  //fechaPrecioRebajadoHasta!:string;
  //productosRelacionados!:string; //ventas dirigidas(prod sustituto por la calidad, mas caros)
  //productosPromocion!:string;
  imagen: string = 'no-imagen.jpg';
	visibleEnTienda: boolean=false;
	activo: boolean = true;
  color?:Color;
  material?: Material;
  categoria?:Categoria;
  uso?: Uso;
  estadoProducto!: EstadoProducto;
}


//
