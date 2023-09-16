package com.sst.springapireportes.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sst.springapireportes.auditoria.modelo.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="equipocliente",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idEquipo",callSuper = false)
public class EquipoCliente extends Auditable implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idEquipo ;
  @ColumnTransformer(
    read = "substring(codigoequipo, 5)", // Para leer desde la base de datos sin el prefijo
    write = "concat('CCE_', ?)" // Para escribir en la base de datos con el prefijo
  )
  @Column(updatable = false,nullable=false)
  private String codigoequipo;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
  @Column(nullable=false)
  private String nombre;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
  @Column(nullable=false)
  private String categoria_equipo;

  @Column
  private String capacidad_maxima ;
  @Column
  private String capacidad_minima ;
  @Column
  private String resolucion;
  @Column
  private String divisiones ;
  @Column
  private String observaciones ;
  @Lob
  @Column(columnDefinition = "LONGBLOB",nullable = false)
  private byte[] imagen_equipo;
  @Column
  private String unidad_medida;
  @Column
  private String instrumento;
  @Column
  private String mide ;
  @Column(nullable=false)
  private String lista_precio;
  @Column
  private String cmc_equipo;
  @Column
  private String fabricante_receptor;
  @Column
  private String modelo_receptor ;
  @Column
  private String serie_receptor ;
  @Column
  private String id_interno_receptor ;
  @Column
  private String fabricante_sensor;
  @Column
  private String modelo_sensor ;
  @Column
  private String id_interno_sensor ;
  @Column
  private String serie_sensor;
  @Column
  private String fabricante_indicador ;
  @Column
  private String modelo_indicador;
  @Column
  private String serie_indicador;
  @Column
  private String id_interno_indicador;
  @Temporal(TemporalType.DATE)
  private Date createAt;
  @Column(columnDefinition = "boolean default true") // Valor predeterminado establecido en "true" por defecto
  private boolean activo= true;
  private static final long serialVersionUID= 1L;
  @ManyToOne(fetch = FetchType.LAZY, optional=false)
  @JoinColumn(name = "idCliente")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Cliente cliente;

  @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
  private Set<ReporteTecnico> reporteTecnicos= new HashSet<>();
  @PrePersist
  public void prePersist() {
    createAt = new Date();

  }

}
