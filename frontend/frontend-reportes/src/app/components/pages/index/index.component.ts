import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from '../../service/authentication.service';  // Asegúrate de importar tu servicio de autenticación

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {
  isLogged = false;
  nombreUsuario = '';
  welcomeMessage!: string; // Agrega esta variable para almacenar el mensaje de bienvenida

  constructor(
    private httpClient: HttpClient,
    private authService: AuthenticationService// Inyecta tu servicio de autenticación aquí
  ) {}

  ngOnInit(): void {

    // Realiza la solicitud HTTP al backend
    this.httpClient.get<string>('/api/v1/home').subscribe(
      response => {
        this.welcomeMessage = response; // Almacena el mensaje de bienvenida
      },
      error => {
        console.error('Error al obtener el mensaje de bienvenida:', error);
      }
    );
  }
}

