import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/pages/index/index.component';
import { ClientesComponent } from './components/pages/cliente/cliente.component'; 
import { EquipoclienteComponent } from './components/pages/equipocliente/equipocliente.component';
import { OrdentrabajoComponent } from './components/pages/ordentrabajo/ordentrabajo.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { ReportetecnicoComponent } from './components/pages/reportetecnico/reportetecnico.component';
import { authGuard } from './components/configuraciones/auth.guard';
import { LoginComponent } from './components/pages/login/login.component';
import { FormComponent } from './components/pages/cliente/form/form.component';
const routes: Routes = [

  {path:'index', component: IndexComponent},
  {path:'login',component: LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'cliente',component:ClientesComponent, canActivate:[authGuard]},
  {path:'clientes/form',component: FormComponent, canActivate:[authGuard]},
  {path:'clientes/form/:id',component: FormComponent, canActivate:[authGuard]},
  {path:'equipocliente',component:EquipoclienteComponent, canActivate:[authGuard]},
  {path:'ordentrabajo',component:OrdentrabajoComponent, canActivate:[authGuard]},
  {path:'reportetecnico',component:ReportetecnicoComponent, canActivate:[authGuard]},
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
