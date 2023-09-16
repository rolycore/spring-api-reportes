import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { RegisterRequest } from 'src/app/components/models/register-request.model';
import { AuthenticationResponse } from 'src/app/components/models/authentication-response.model';
import { AuthenticationRequest } from 'src/app/components/models/authentication-resquest.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private usuario: string | null = null;
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private baseUrl = 'http://localhost:8080/api/v1/auth'; // Reemplaza con la URL de tu backend

  constructor(private http: HttpClient, private router: Router) {

  }

// Método para verificar si el usuario está autenticado
isAuthenticated(authRequest: AuthenticationRequest): boolean {
  // Aquí deberías implementar la lógica de verificación de autenticación
  // Esto puede incluir la validación de las credenciales en el objeto authRequest
  // y la comprobación si el usuario está autorizado.

  // Ejemplo simplificado:
  if (authRequest.email=== 'usuario' && authRequest.password === 'contraseña') {
    return true; // Las credenciales son válidas, el usuario está autenticado
  } else {
    return false; // Las credenciales no son válidas, el usuario no está autenticado
  }
}
  // Método para obtener el nombre de usuario
  getUsuario(): string | null {
    return this.usuario;
  }

  register(
    registerRequest: RegisterRequest
  ) {
    return this.http.post<AuthenticationResponse>
    (`${this.baseUrl}/register`, registerRequest);
  }

  login(authRequest: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(
      `${this.baseUrl}/authenticate`,
      authRequest
    ).pipe(
      catchError((e) => {
        // Aquí puedes manejar el error y verificar si es debido a credenciales incorrectas
        if (e.status === 401) {
          // El servidor devolvió un error 401 Unauthorized, lo que indica credenciales incorrectas
          Swal.fire('Credenciales incorrectas',e.error.mensaje,'error');
          return throwError(e);
        } else {
          // Otro tipo de error, puedes manejarlo de acuerdo a tus necesidades
          console.error(e.error.mensaje);

          Swal.fire('Error al ingresar',e.error.mensaje,'error');
          return throwError(e);
        }
      })
    );
  }


getToken(){
  return localStorage.getItem('token');
}
  refreshToken(): Observable<void> {
    // Puedes manejar la lógica del refresh token aquí si es necesario
    return this.http.post<void>(
      `${this.baseUrl}/refresh-token`,
      {}
    );
  }
  cerrarSesion(): void {
    // Elimina el token de acceso almacenado localmente
    localStorage.removeItem('token'); // O ajusta según donde almacenas el token

    // Redirige al usuario a la página de inicio de sesión o a la página principal
    // Puedes utilizar el Router de Angular para hacer la redirección
    this.router.navigate(['/login']); // Reemplaza '/login' con la ruta de inicio de sesión
  }
}
