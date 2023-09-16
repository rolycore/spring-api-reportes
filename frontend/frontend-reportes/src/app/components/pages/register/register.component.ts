import { Component } from '@angular/core';
import { RegisterRequest } from '../../models/register-request.model';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  authResponse: any; // Variable para almacenar la respuesta de autenticación

  registerRequest: RegisterRequest = {};

  message = '';

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  registerUser() {
    this.message = '';
    this.authService.register(this.registerRequest)
      .subscribe({
        next: (response) => {
          if (response) {
            this.authResponse = response; // Almacena la respuesta de autenticación
            // Si la respuesta incluye un token, puedes acceder a él como this.authResponse.access_token
            const accessToken = this.authResponse.access_token;
            // También puedes acceder a this.authResponse.refresh_token si es necesario
          } else {
            // inform the user
            this.message = 'Account created successfully\nYou will be redirected to the Login page in 3 seconds';
            setTimeout(() => {
              this.router.navigate(['login']);
            }, 3000);
          }
        }
      });
  }
}
