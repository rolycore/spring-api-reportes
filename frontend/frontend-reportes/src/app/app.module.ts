import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './components/pages/index/index.component';
import {  ClientesComponent } from './components/pages/cliente/cliente.component';
import { EquipoclienteComponent } from './components/pages/equipocliente/equipocliente.component';
import { OrdentrabajoComponent } from './components/pages/ordentrabajo/ordentrabajo.component';
import { MenuComponent } from './components/menu/menu.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './components/pages/register/register.component';
import { ReportetecnicoComponent } from './components/pages/reportetecnico/reportetecnico.component';
import { LoginComponent } from './components/pages/login/login.component';
import { HeaderComponent } from './components/pages/header/header.component';
import { FooterComponent } from './components/pages/footer/footer.component';
import { FormComponent } from './components/pages/cliente/form/form.component';
import { ClienteService } from './components/service/cliente.service';
import { registerLocaleData } from '@angular/common';
import localPa from '@angular/common/locales/es-PA';

@NgModule({
  declarations: [

    AppComponent,
    ClientesComponent,
    EquipoclienteComponent,
    OrdentrabajoComponent,
    MenuComponent,
    RegisterComponent,
    ReportetecnicoComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    FormComponent
  ],
  imports: [

    BrowserModule,
    AppRoutingModule,
     HttpClientModule,
    FormsModule,
    ReactiveFormsModule

  ],
  providers: [ClienteService,{provide: LOCALE_ID, useValue: 'es-PA'}

    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
