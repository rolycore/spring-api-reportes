package com.sst.springapireportes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.sst.springapireportes.user.Permission.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors()
            .and()
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
            .requestMatchers(
                    "/api/v1/auth/**",
                    "/api/v1/home/",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html").permitAll()
      // Privilegios sobre rutas raíces comunes
      .requestMatchers(
        "/api/v1/management/**",
        "/api/v1/clientes/**",
        "/api/v1/equipos-clientes/**",
        "/api/v1/calibraciones/**",
        "/api/v1/cotizacionesCabe/**",
        "/api/v1/cotizacionesDetalle/**",
        "/api/v1/estados/**",
        "/api/v1/instrumentos/**",
        "/api/v1/laboratorios/**",
        "/api/v1/precios/**",
        "/api/v1/servicios/**",
        "/api/v1/solicitudesCabe/**",
        "/api/v1/solicitudesDetalle/**",
        "/api/v1/unidadesMedida/**"
      ).hasAnyRole("ADMIN", "MANAGER")

//Rutas raices demo
        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD Cliente Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/clientes/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/clientes/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD Equipo cliente Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/equipos-clientes/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/equipos-clientes/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/equipos-clientes/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/equipos-clientes/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
      //Permisos para CRUD calibraciones Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/calibraciones/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/calibraciones/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/calibraciones/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/calibraciones/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

      //Permisos para CRUD cotizacionesCabe Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/cotizacionesCabe/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/cotizacionesCabe/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/cotizacionesCabe/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/cotizacionesCabe/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
      //Permisos para CRUD cotizacionesDetalle Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/cotizacionesDetalle/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD cotizacionesDetalle Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/cotizacionesDetalle/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/cotizacionesDetalle/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD Estados Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/estados/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/estados/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/estados/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/estados/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD instrumentos Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/instrumentos/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/instrumentos/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/instrumentos/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/instrumentos/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD laboratorios Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/laboratorios/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/laboratorios/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/laboratorios/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/laboratorios/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD precios Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/precios/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/precios/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/precios/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/precios/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD servicios Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/servicios/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/servicios/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/servicios/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/servicios/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD SolicitudesCabe Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/solicitudesCabe/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/solicitudesCabe/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/solicitudesCabe/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/solicitudesCabe/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD solicitudesDetalle Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/solicitudesDetalle/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/solicitudesDetalle/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/solicitudesDetalle/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/solicitudesDetalle/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//Permisos para CRUD unidadesMedida Rol ADMIN, Manager
      .requestMatchers(HttpMethod.GET, "/api/v1/unidadesMedida/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
      .requestMatchers(HttpMethod.POST, "/api/v1/unidadesMedida/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
      .requestMatchers(HttpMethod.PUT, "/api/v1/unidadesMedida/*").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
      .requestMatchers(HttpMethod.DELETE, "/api/v1/unidadesMedida/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())



      .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/v1/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
}
