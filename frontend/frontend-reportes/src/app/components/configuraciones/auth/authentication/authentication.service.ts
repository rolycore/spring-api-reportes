import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { RegisterRequest } from 'src/app/components/models/register-request.model';
import { AuthenticationResponse } from 'src/app/components/models/authentication-response.model';
import { AuthenticationRequest, Credentials } from 'src/app/components/models/authentication-resquest.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl = 'http://localhost:8080/api/v1/auth'; // Reemplaza con la URL de tu backend

  constructor(private httpClient: HttpClient) {}

  register(request: RegisterRequest): Observable<AuthenticationResponse> {
    return this.httpClient.post<AuthenticationResponse>(
      `${this.baseUrl}/register`,
      request
    );
  }
  authenticate(creds:Credentials){
  return this.httpClient.post('/api/v1/auth/authenticate',creds,{
    observe:'response'
  }).pipe(map((response:HttpResponse<any>)=>{
    const body = response.body;
    const headers = response.headers;
    const bearerToken= headers.get('Authorization')!;
    const token = bearerToken.replace('Bearer ','');
    localStorage.setItem('token',token);
    return body;
  }))
}
  // Implementa métodos para autenticar y refrescar tokens de manera similar
 /* authenticate(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.httpClient.post<AuthenticationResponse>(
      `${this.baseUrl}/authenticate`,
      request
    );
  }*/

getToken(){
  return localStorage.getItem('token');
}
  refreshToken(): Observable<void> {
    // Puedes manejar la lógica del refresh token aquí si es necesario
    return this.httpClient.post<void>(
      `${this.baseUrl}/refresh-token`,
      {}
    );
  }
}
