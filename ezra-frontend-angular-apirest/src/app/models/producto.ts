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
  nombre!:string;
  descripcion!:string;
  medidas?:string
  peso?:string
  imagen!:string;
  galeriaProducto: GaleriaProducto[]=[]
  //marca?:string
  //modelo?:string
	unbralPocaCantidad:number=0;
	unbralAgotadaCantidad:number=0;
  cantidadStock:number=0
	minCantidadPedido:number=1;
	maxCantidadPedido:number=0;
	gruposDe:number=0;
  costoUnitario:number=0
  costoUnitarioEmpaque:number=0
  precioBruto:number=0
  precioBrutoRebajado:number=0;
  precioNeto:number=0
  precioNetoRabajado:number=0;
  createAt!:string
  fechaPrecioRebajadoDesde!:string;
	fechaPrecioRebajadoHasta!:string;
	productosRelacionados!:string; //ventas dirigidas(prod sustituto por la calidad, mas caros)
	productosPromocion!:string;

  color?:Color;
  material?: Material;
  //materialId?:number
  //origen!:Origen;
  //origenId!:number
  //empaque!:Empaque;
  //empaqueId!:number
  categoria!:Categoria;
  //categoriaId!:number
  uso!: Uso;
  //usoId!:number;
  //genericos: GenericosDeProducto[]=[]
}


//
