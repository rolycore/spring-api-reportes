import { User } from "./user.model";

export class Token {

  constructor(
  public id: number,
  public token: string,
  public tokenType: TokenType,
  public revoked: boolean,
  public expired: boolean,
  public user: User,
  ){

  }
}
// Enumeración TokenType (si es necesario)
export enum TokenType {
  BEARER = 'BEARER',
  OTRO_TIPO = 'OTRO_TIPO'
}
