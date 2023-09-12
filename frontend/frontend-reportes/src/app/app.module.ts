import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './components/index/index.component';
import { ClienteComponent } from './components/cliente/cliente.component';
import { EquipoclienteComponent } from './components/equipocliente/equipocliente.component';
import { OrdentrabajoComponent } from './components/ordentrabajo/ordentrabajo.component';
import { MenuComponent } from './components/menu/menu.component';
import { AuthenticationComponent } from './components/configuraciones/auth/authentication/authentication.component';
import { RoleListComponent } from './components/configuraciones/role-list/role-list.component';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from './components/configuraciones/auth/authentication/authentication.service';
import { TokenService } from './components/service/token.service';
import { RoleService } from './components/service/roleservice.service';
import { AuthInterceptor } from './components/configuraciones/auth/helpers/auth.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    ClienteComponent,
    EquipoclienteComponent,
    OrdentrabajoComponent,
    MenuComponent,
    AuthenticationComponent,
    RoleListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
     HttpClientModule,
    FormsModule

  ],
  providers: [
   {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi:true},
    AuthenticationService,
    TokenService,
    RoleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
