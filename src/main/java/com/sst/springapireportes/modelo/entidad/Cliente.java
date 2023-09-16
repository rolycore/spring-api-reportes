package com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente;

import java.io.Serializable;
import java.util.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sstproyects.springboot.backend.apirest.auditoria.modelo.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;


@Entity
@Table(name="cliente",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCliente", callSuper = false)
public class Cliente extends Auditable implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCliente;
	@NotEmpty(message =" no puede estar vacio")
	@NotBlank(message =" no puede estar vacio")
	@Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
	@Column(nullable=false)
	private String nombre;
	@NotEmpty(message =" no puede estar vacio")
	@NotBlank(message =" no puede estar vacio")
	@Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
	@Column(nullable=false)
	private String apellido;
	@NotEmpty(message =" no puede estar vacio")
	@NotBlank(message =" no puede estar vacio")
	@Email(message=" no es una dirección bien formada")
	@Column(nullable=false, unique=true)
	private String email;
	@NotEmpty(message =" no puede estar vacio")
	@Column(name="create_at",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createAt;
  @Column
  private String telefono_empresa;

  @Column(nullable=false, unique=true, updatable = false)
  @ColumnTransformer(
    read = "substring(cod_cliente, 5)", // Para leer desde la base de datos sin el prefijo
    write = "concat('COD_', ?)" // Para escribir en la base de datos con el prefijo
  )
  private String cod_cliente;
  @Column
  private String razon_social;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String nombre_comercial;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String  ruc;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String dv;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String direccion;
  @Column
  private String telefono_jefe;
  @Column
  private String celular_jefe;
  @Column
  private String correo_electronico;
  @Column
  private String actividad_economica;
  @Column
  private String abreviatura;
  @Column
  private String nombre_contacto;
  @Column
  private String cargo_servicio;
  @Column
  private String celular_servicio;
  @Column
  private String correo_servicio;
  @Column
  private String telefono_servicio;
  @Column
  private String nombre_cobro;
  @Column
  private String cargo_cobro;
  @Column
  private String telefono_cobro;
  @Column
  private String celular_cobro;
  @Column
  private String correo_cobro;
  @Column// Valor predeterminado establecido en "true" por defecto
  private boolean activo= true;
  private static final long serialVersionUID= 1L;
  // Relación con Equipos
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<EquipoCliente> equipos = new HashSet<>();
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<SolicitudCabe> solicitudes = new HashSet<>();
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<CotizacionCabe> cotizaciones = new HashSet<>();
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<Calibracion> calibraciones = new HashSet<>();
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<OrdenTrabajo> ordenTrabajos = new HashSet<>();
  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<ReporteTecnico> reporteTecnicos = new HashSet<>();
	@PrePersist
	public void prePersist() {
		createAt = new Date();

    /*if (this.id != null) {
    if (this.id <= 9) {
      this.cod_cliente = "CC-000" + String.valueOf(this.id);
    }
   else if (this.id < 10) {
      this.cod_cliente = "CC-00" + String.valueOf(this.id);
    } else if (this.id < 100) {
      this.cod_cliente = "CC-0" + String.valueOf(this.id);
    } else {
      this.cod_cliente = "CC-" + String.valueOf(this.id);
    }
    }*/

  }


}
