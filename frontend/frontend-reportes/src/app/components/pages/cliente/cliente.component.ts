import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../models/cliente'; 
import { ClienteService } from '../../service/cliente.service';
import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';


@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.scss']
})
export class ClientesComponent implements OnInit {
  clientes!: Cliente[];
  constructor(private clientesService: ClienteService) {}
  ngOnInit(): void {
    this.clientesService.getClientes().pipe(
      tap(clientes=>{
        this.clientes = clientes;
        console.log('Clientes.Component: tap 3')
        clientes.forEach(cliente=>{
          console.log(cliente.nombre);
        });
      })
    ).subscribe();
  }
  //Metodo delete
  delete(cliente: Cliente): void {
    const swalWithBootstrapButtons = swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.clientesService.delete(cliente.id).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente);
          },
          error => {
            swalWithBootstrapButtons.fire(
              'Error',
              'Ocurrió un error al eliminar el cliente.',
              'error'
            );
          },
          () => {
            swalWithBootstrapButtons.fire(
              'Cliente Eliminado!',
              `Cliente ${cliente.nombre} ${cliente.apellido} eliminado con éxito.`,
              'success'
            );
          }
        );
      }
    });
  }
}

