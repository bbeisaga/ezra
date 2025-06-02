import { Producto } from "./producto";

export class Categoria {
  id!: number;
  nombre!: string;
  descripcion!:string;
  activa!:boolean
  orden!: number;
  productos :Producto[]=[];
}
