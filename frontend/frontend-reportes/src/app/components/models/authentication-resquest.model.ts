export class AuthenticationRequest {
  email: string;
  password: string;

  constructor(email: string, password: string) {
    this.email = email;
    this.password = password;
  }
}

export interface Credentials{
  email: string;
  password: string;
}
