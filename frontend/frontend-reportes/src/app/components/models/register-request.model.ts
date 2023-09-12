import { Role } from "./role.model";

export class RegisterRequest {
  public roles: Role[] = []; // Propiedad para almacenar los roles disponibles

  constructor(
    public firstname: string,
    public lastname: string,
    public email: string,
    public password: string,
    public role: Role // Asegúrate de importar 'Role' si es necesario
  ) {}
}
