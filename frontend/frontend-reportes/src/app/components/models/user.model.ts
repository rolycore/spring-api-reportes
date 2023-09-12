import { Role } from "./role.model";
import { Token } from "./token.model";

export class User {

  constructor(
   public id: number,
   public firstname: string,
   public lastname: string,
   public  email: string,
   public password: string,
   public role: Role, // Asegúrate de importar el modelo Role si es necesario
   public tokens: Token,
  public  activo: boolean,

  ){
    
  }
}
