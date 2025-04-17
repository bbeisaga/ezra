import { Role } from "./role";

export class Usuario {
  id!: number;
  username!: string;
  password!: string;
  nombres!: string;
  apellidos!: string;
  email!: string;
  //roles: string[] = []; //Este se usa en AuthService
  roles: Role[] =[];  //Se usa para modulo usuarios se ha cambviado revisar

}
