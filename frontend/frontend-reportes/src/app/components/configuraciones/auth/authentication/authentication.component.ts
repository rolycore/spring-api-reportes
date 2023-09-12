import { Component } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { RegisterRequest } from 'src/app/components/models/register-request.model';
import { NgForm } from '@angular/forms';
import { Credentials } from 'src/app/components/models/authentication-resquest.model';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss']
})
export class AuthenticationComponent {

  creds: Credentials={
    email:'',
    password:''
  };
  router: any;
  constructor(private authService: AuthenticationService) {}
authenticateUser(form: NgForm){
console.log('form value',form.value);
this.authService.authenticate(this.creds).subscribe(response =>{
  this.router.navigate(['/']);
})
}
  registerUser(request: RegisterRequest): void {
    this.authService.register(request).subscribe(
      (response) => {
        // Manejar la respuesta del backend aquí
      },
      (error) => {
        // Manejar errores aquí
      }
    );
  }
}
