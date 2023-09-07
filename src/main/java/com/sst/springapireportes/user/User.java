package com.sst.springapireportes.user;

import com.sst.springapireportes.auditoria.modelo.Auditable;
import com.sst.springapireportes.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
@EqualsAndHashCode(of = "id",callSuper=false)
public class User extends Auditable implements UserDetails {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Size(min=4, max=100, message="el tamaño tiene que estar entre 4 y 12")
  @Column(nullable=false)
  private String firstname;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Size(min=4, max=100, message="el tamaño tiene que estar entre 4 y 12")
  @Column(nullable=false)
  private String lastname;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Email(message="no es una dirección bien formada")
  @Column(nullable=false, unique=true)
  private String email;
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Size(min=4, max=25, message="el tamaño tiene que estar entre 4 y 25")
  @Column(nullable=false)
  private String password;

  @Enumerated(EnumType.STRING)
  @NotEmpty(message =" no puede estar vacio")
  @NotBlank(message =" no puede estar vacio")
  @Column(nullable=false)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;
  @Column(columnDefinition = "boolean default true") // Valor predeterminado establecido en "true" por defecto
  private boolean activo;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
