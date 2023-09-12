import { Component, OnInit } from '@angular/core';
import { RoleService } from '../../service/roleservice.service';
import { Role } from '../../models/role.model'; // Importa el modelo Role

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.scss']
})
export class RoleListComponent implements OnInit {
  roles: Role[] = []; // Cambia el tipo de 'roles' a Role[]

  constructor(private roleService: RoleService) {}

  ngOnInit(): void {
    this.roleService.getAllRoles().subscribe((data) => {
      this.roles = data; // Asigna los roles devueltos por el servicio
    });
  }
}
