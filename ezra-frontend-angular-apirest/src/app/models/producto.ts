import { Categoria } from "./categoria"
import { Color } from "./color"
import { Empaque } from "./empaque"
import { Material } from "./material"
import { Origen } from "./origen"
import { Uso } from "./uso"
import { GenericosDeProducto } from './genericos-de-producto';

export class Producto {
  id!: number
  nombre!:string
  medidas?:string
  peso?:string
  //marca?:string
  //modelo?:string
  cantidadStock:number=0
  costoUnitario:number=0
  costoUnitarioEmpaque:number=0
  precioBruto:number=0
  precioNeto:number=0
  createAt!:string
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
