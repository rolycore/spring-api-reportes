package com.sstproyects.springboot.backend.apirest.controllers.serviciocliente;
import com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente.IEquipoClienteDao;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.EquipoCliente;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IClienteService;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IEquipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
  public ResponseEntity<EquipoCliente> createEquipo(@RequestParam("imagen") MultipartFile imagen, @ModelAttribute EquipoCliente equipo) {
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
  public ResponseEntity<EquipoCliente> updateEquipo(@PathVariable Long id, @RequestParam("imagen") MultipartFile imagen, @ModelAttribute EquipoCliente equipo) throws IOException {
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
    }catch (IOException e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
