package com.sst.springapireportes.config.accescontrol;

import com.sst.springapireportes.auditoria.modelo.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_accescontrol")
@EqualsAndHashCode(of = "id", callSuper = false)
public class AccessControlConfig extends Auditable implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String pathPattern;
  private String accessExpression;
  @Column(nullable = false)
  private String descriptionaccesscontrol;
}
