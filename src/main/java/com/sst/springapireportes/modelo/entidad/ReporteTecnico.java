package com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sstproyects.springboot.backend.apirest.auditoria.modelo.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
@Entity
@Table(name="reportetecnico")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idreptec", callSuper = false)
public class ReporteTecnico extends Auditable implements Serializable{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long idreptec;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String no_reporte_tecnico;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String no_cotizacion;
  @Column
  @NotBlank(message =" no puede estar vacio")
  private String tecnico;
  @Column
  @Temporal(TemporalType.TIME)
  private LocalTime horaentrada;
  @Column
  @Temporal(TemporalType.TIME)
  private LocalTime horasalida;
  @Column
  private String horaviajes;
  @Column
  @Temporal(TemporalType.DATE)
  private Date fechareporte;
  @Column
  private String contacto;
  @Column
  private String direccion;
  // 1	DATOS TÉCNICOS DEL INSTRUMENTO
  @Column
  private String marca;
  @Column
  private String modelo;
  @Column
  private String no_serie;
  @Column
  private String ubicacion_equipo;
  @Column
  private String idinterno;
  @Column
  private String capacidad;
  @Column
  private String resolucion;
//2	SERVICIO A REALIZAR

  @Column
  private boolean calibracion;
  @Column
  private boolean instalacion;
  @Column
  private boolean verificacion;
  @Column
  private boolean entregaequipo;
  @Column
  private boolean gestionmetrologica;
  @Column
  private boolean retiroequipo;
  @Column
  private boolean inspeccion;
  @Column
  private boolean otros;
  @Column
  private String observaciones;

  //3	DEFICIENCIAS ENCONTRADAS.
  @Column
  private boolean desnivel;
  @Column
  private boolean vibraciones;
  @Column
  private boolean averias;
  @Column
  private boolean erroresindicador;
  @Column
  private boolean soporteinadecuadas;
  @Column
  private boolean faltacomponente;
  @Column
  private boolean suceidad;
  @Column
  private boolean corrienteaire;
  @Column
  private boolean insectos;
  @Column
  private boolean golpe;
  @Column
  private boolean fuentexternacalor;
  @Column
  private boolean configuracion;
  @Column
  private String observaciones2;

  //4	OTROS TRABAJOS REALIZADOS.
  @Column
  private boolean nivelacion;
  @Column
  private boolean limpieza;
  @Column
  private boolean ajusteslinealidad;
  @Column
  private boolean configuracion1;
  @Column
  private boolean ajusteexcentricidad;
  @Column
  private boolean reemplazo;
  @Column
  private String observaciones3;
  //5	ESTATUS FINAL DEL SERVICIO.
  @Column
  private boolean completo;
  @Column
  private boolean incompleto;
  @Column
  private String observaciones4;

  //6	OBSERVACIONES DEL CLIENTE
  @Column
  private String nota;
  @Column
  private String recibidopor;
  @Column
  @Temporal(TemporalType.DATE)
  private Date fecha;
  @Column
  private boolean activo=true;
  private static final long serialVersionUID= 1L;



  @ManyToOne(fetch = FetchType.LAZY, optional=false)
  @JoinColumn(name = "idCliente")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Cliente cliente;
  @ManyToOne(fetch = FetchType.LAZY, optional=false)
  @JoinColumn(name = "idEquipo")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private EquipoCliente equipo;


}
