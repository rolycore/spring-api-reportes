package com.sst.springapireportes.controllers;

import com.sst.springapireportes.modelo.entidad.Cliente;
import com.sst.springapireportes.modelo.entidad.EquipoCliente;
import com.sst.springapireportes.modelo.services.IClienteService;
import com.sst.springapireportes.modelo.services.IEquipoClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.Base64;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
@Tag(name = "EquipoClientes",description = "Registro de equipo de clientes")
@SecurityRequirement(name = "bearer-key")
@SuppressWarnings("all")
public class EquipoClienteRestController {
  @Autowired
  private IEquipoClienteService equipoClienteService;
  @Autowired
  private IClienteService clienteService;

  // Buscar todos los equipos de clientes
  @GetMapping("/equipos-clientes")
  public ResponseEntity<List<EquipoCliente>> findAllEquipos() {
    List<EquipoCliente> equipos = equipoClienteService.findAll();
    return ResponseEntity.ok(equipos);
  }

  // Buscar equipo de cliente por ID
  @GetMapping("/equipos-clientes/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<EquipoCliente> findEquipoById(@PathVariable Long id) {
    EquipoCliente equipo = equipoClienteService.findById(id);
    if (equipo != null) {
      return ResponseEntity.ok(equipo);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Crear equipo de cliente
  @PostMapping("/equipos-clientes")
  @Operation(
          summary = "Crear un equipo cliente con imagen",
          description = "Este endpoint permite crear un equipo cliente y subir una imagen en formato byte[].",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Equipo cliente creado con éxito"
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Solicitud inválida"
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Error interno del servidor"
                  )
          }
  )
  public ResponseEntity<EquipoCliente>  createEquipo(
          @RequestParam("imagen") @Parameter(
                  name = "imagen",
                  description = "Archivo de imagen en formato byte[].",
                  required = true,
                  content = @Content(
                          mediaType = "application/octet-stream",
                          schema = @Schema(type = "string", format = "binary")
                  )
          ) MultipartFile imagen,
          @ModelAttribute @Parameter(
                  name = "equipo",
                  description = "Datos del equipo cliente a crear.",
                  required = true
          ) EquipoCliente equipo) {

    try {
      Optional<Cliente>clienteOptional= Optional.ofNullable(clienteService.findById(equipo.getCliente().getIdCliente()));
      if(!clienteOptional.isPresent()){
        return ResponseEntity.unprocessableEntity().build();
      }
      equipo.setCliente(clienteOptional.get());
      //equipo.setIdcodigocliente(clienteOptional.get().getCod_cliente());

      byte[] imagenBytes = imagen.getBytes();
      equipo.setImagen_equipo(imagenBytes);

      EquipoCliente createdEquipo = equipoClienteService.createOrUpdate(equipo);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdEquipo);
    } catch (IOException e) {
      // Manejo de errores en caso de que la conversión falle.
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  // Actualizar equipo de cliente por ID
  @PutMapping("/equipos-clientes/{id}")
  @Operation(
          summary = "Actualizar un equipo cliente con nueva imagen",
          description = "Este endpoint permite actualizar un equipo cliente y subir una nueva imagen en formato byte[].",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Equipo cliente actualizado con éxito"
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Solicitud inválida"
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Equipo cliente no encontrado"
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Error interno del servidor"
                  )
          }
  )
  public ResponseEntity<EquipoCliente> updateEquipo(
          @PathVariable @Parameter(
                  name = "id",
                  description = "ID del equipo cliente a actualizar.",
                  required = true
          ) Long id,
          @RequestParam("imagen") @Parameter(
                  name = "imagen",
                  description = "Archivo de imagen en formato byte[].",
                  required = true,
                  content = @Content(
                          mediaType = "application/octet-stream",
                          schema = @Schema(type = "string", format = "binary")
                  )
          ) MultipartFile imagen,
          @ModelAttribute @Parameter(
                  name = "equipo",
                  description = "Datos del equipo cliente actualizado.",
                  required = true
          ) EquipoCliente equipo)throws IOException {
    try {
      Optional<Cliente>clienteOptional= Optional.ofNullable(clienteService.findById(equipo.getCliente().getIdCliente()));
      if(!clienteOptional.isPresent()){
        return ResponseEntity.unprocessableEntity().build();
      }
      Optional<EquipoCliente> equipoClienteOptional= Optional.ofNullable(equipoClienteService.findById(id));
      if(!equipoClienteOptional.isPresent()){
        return ResponseEntity.unprocessableEntity().build();
      }
      equipo.setCliente(clienteOptional.get());
      byte[] imagenBytes = imagen.getBytes();
      equipo.setImagen_equipo(imagenBytes);
      EquipoCliente updatedEquipo = equipoClienteService.createOrUpdate(equipo);
      return ResponseEntity.ok(updatedEquipo);
    }catch (Exception e) {
      if (e instanceof FileNotFoundException) {
        // Manejo de error si el equipo cliente no se encuentra
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      } else {
        // Manejo de errores en caso de que la actualización falle.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
  }

  // Borrar equipo de cliente por ID
  @DeleteMapping("/equipos-clientes/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    boolean success = equipoClienteService.delete(id);
    if (!success) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }
}
