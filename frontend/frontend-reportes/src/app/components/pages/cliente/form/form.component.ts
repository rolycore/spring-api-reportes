import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/components/models/cliente'; 
import { ClienteService } from 'src/app/components/service/cliente.service'; 
import { Router, ActivatedRoute } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  public formulario!: FormGroup;
  activeTab: number = 1;
  public cliente: Cliente = new Cliente();
  public titulo: string = 'Crear Cliente';
  public errores: string[] = [];

  constructor(
    
    private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      cod_cliente:['', Validators.required],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      nombre_comercial: ['', Validators.required],
      abreviatura: ['', Validators.required],
      ruc: ['', Validators.pattern('[0-9]{11}')],
      dv: ['', Validators.pattern('[0-9]{1}')],
      actividad_economica: ['', Validators.required],
      direccion:['', Validators.required],
      correo_electronico:['',[Validators.required, Validators.email]],
      nombre_contacto: ['', Validators.required],
      telefono_servicio: ['', Validators.required],
      celular_servicio: ['', Validators.required],
      celular_jefe: ['', Validators.required],
      telefono_jefe: ['', Validators.required],
      nombre_cobro: ['', Validators.required],
      cargo_cobro: ['', Validators.required],
      correo_cobro: ['', [Validators.required, Validators.email]],
      celular_cobro: ['', Validators.required],
      telefono_cobro: ['', Validators.required],
    });

    this.cargarCliente();
  }

  /* cargarCliente(): void {
    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clienteService
          .getCliente(id)
          .subscribe((cliente) => (this.cliente = cliente));
      }
    });
  }*/
  cargarCliente(): void {
    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => {
          this.cliente = cliente;
          this.formulario.patchValue({
            nombre: cliente.nombre,
            apellido: cliente.apellido,
            email: cliente.email,
            nombre_comercial: cliente.nombre_comercial,
            abreviatura: cliente.abreviatura,
            ruc: cliente.ruc,
            dv: cliente.dv,
            actividad_economica: cliente.actividad_economica,
            nombre_contacto: cliente.nombre_contacto,
            telefono_servicio: cliente.telefono_servicio,
            celular_servicio: cliente.celular_servicio,
            celular_jefe: cliente.celular_jefe,
            telefono_jefe: cliente.telefono_jefe,
            nombre_cobro: cliente.nombre_cobro,
            cargo_cobro: cliente.cargo_cobro,
            correo_cobro: cliente.correo_cobro,
            celular_cobro: cliente.celular_cobro,
            telefono_cobro: cliente.telefono_cobro
            // Asigna los valores de los otros campos del formulario aquí
          });
        });
      }
    });
  }

  create(): void {
    this.clienteService.create(this.cliente).subscribe(
      (cliente) => {
        this.router.navigate(['/clientes']);
        swal.fire(
          'Nuevo cliente',
          `El cliente ${this.cliente.nombre} ha sido creado con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }
  update(): void {
    this.clienteService.update(this.cliente).subscribe(
      (json) => {
        this.router.navigate(['/componets/clientes']);
        swal.fire(
          'Cliente Actualizado',
          `${json.mensaje}: ${this.cliente.nombre} ${this.cliente.apellido}`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  onSubmit() {
    if (this.formulario) {
      if (this.formulario.valid) {
        // Realizar acciones con los datos del formulario
        this.create();

        console.log('Formulario válido. Enviar datos:', this.formulario.value);
      } else {
        // El formulario no es válido, mostrar errores o realizar acciones adicionales
        console.log('Formulario inválido. No se puede enviar.');
      }
    }
  }
}
