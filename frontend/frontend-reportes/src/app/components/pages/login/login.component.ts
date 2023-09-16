import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service'; // Asegúrate de importar tu servicio de autenticación
import { AuthenticationRequest } from '../../models/authentication-resquest.model';   // Ajusta esta importación según tu modelo de solicitud
import { AuthenticationResponse } from '../../models/authentication-response.model';  // Importa tu modelo de respuesta
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  public user: AuthenticationRequest = new AuthenticationRequest('', '');
  public titulo: string = 'Login';
  formulario!: FormGroup;
  authRequest: AuthenticationRequest = new AuthenticationRequest('', ''); // Ajusta según tu modelo de solicitud
  authResponse: AuthenticationResponse | null = null;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]]
    });
  }

  authenticate() {
    if (this.formulario.valid) {
      const { email, password } = this.formulario.value;

      this.authService.login({ email, password })
        .subscribe(
          (response: AuthenticationResponse) => {
            localStorage.setItem('token', response.access_token);
            // Puedes realizar la navegación aquí
            this.router.navigate(['index']);
          },
          (error) => {
            // Maneja el error aquí, por ejemplo, muestra un mensaje de error al usuario
            console.error('Error de autenticación:', error);
          }
        );
    }
  }
}
