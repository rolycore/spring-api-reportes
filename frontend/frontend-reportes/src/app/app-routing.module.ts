import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/index/index.component';
import { ClienteComponent } from './components/cliente/cliente.component';
import { EquipoclienteComponent } from './components/equipocliente/equipocliente.component';
import { OrdentrabajoComponent } from './components/ordentrabajo/ordentrabajo.component';
import { MenuComponent } from './components/menu/menu.component';
import { AuthenticationComponent } from './components/configuraciones/auth/authentication/authentication.component';
import { RoleListComponent } from './components/configuraciones/role-list/role-list.component';

const routes: Routes = [
  {path:'', pathMatch: 'full',component: IndexComponent},
  {path:'authentication',component:AuthenticationComponent},
  {path:'menu',component:MenuComponent},
  {path:'role-list',component:RoleListComponent},
  {path:'cliente',component:ClienteComponent},
  {path:'equipocliente',component:EquipoclienteComponent},
  {path:'ordentrabajo',component:OrdentrabajoComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
