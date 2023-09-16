package com.sstproyects.springboot.backend.apirest.controllers.serviciocliente;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ch.qos.logback.core.net.server.Client;
import com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente.IClienteDao;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Clientes")
public class ClienteRestController {  @Autowired
private IClienteDao iClienteDao;
  @Autowired
  private IClienteService clienteService;

  @Operation(
    description = "Get endpoint for clientes",
    summary = "This is a summary for clientes get endpoint",
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200"
      ),
      @ApiResponse(
        description = "Unauthorized / Invalid Token",
        responseCode = "403"
      )
    }

  )
  // Buscar todos clientes
  @GetMapping("/clientes")
  public List<Cliente> index() {
    return iClienteDao.findAll();

  }

  // Buscar Cliente por ID
  @GetMapping("/clientes/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> show(@PathVariable Long id) {
    Cliente cliente = null;
    Map<String, Object> response = new HashMap<>();
    try {
      cliente = clienteService.findById(id);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error al realizar la consulta en la base de datos!");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    if (cliente == null) {
      response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);

  }

  // Crear Cliente por ID
  @PostMapping("/clientes")
  public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente,BindingResult result) {
    Cliente clienteNew = null;

    Map<String, Object> response = new HashMap<>();
    //manejo de errores
    //si contiene errores lo validamos en este if
    if(result.hasErrors()) {

      List<String> errors= result.getFieldErrors()
        .stream()
        .map(err ->"El campo '"+ err.getField()+"' "+err.getDefaultMessage())
        .collect(Collectors.toList());

      response.put("errors", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }


    try {

      clienteNew = clienteService.save(cliente);

    } catch (DataAccessException e) {

      response.put("mensaje", "Error al realizar el insertar en la base de datos!");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "El cliente ha sido creado con éxito!");
    response.put("cliente", clienteNew);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

  }

  // Actualizar Cliente por ID
  @PutMapping("/clientes/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
    Cliente clienteActual = clienteService.findById(id);
    Cliente clienteUpdated = null;
    Map<String, Object> response = new HashMap<>();
    //manejo de errores
    //si contiene errores lo validamos en este if
    if(result.hasErrors()) {

      List<String> errors= result.getFieldErrors()
        .stream()
        .map(err ->"El campo '"+ err.getField()+"' "+err.getDefaultMessage())
        .collect(Collectors.toList());

      response.put("errors", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    if (clienteActual == null) {
      response.put("mensaje", "Error:no se pudo editar, el cliente ID: "
        .concat(id.toString().concat(" no existe en la base de datos!")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }

    try {
      clienteActual.setApellido(cliente.getApellido());
      clienteActual.setNombre(cliente.getNombre());
      clienteActual.setEmail(cliente.getEmail());
      clienteActual.setTelefono_empresa(cliente.getTelefono_empresa());
      clienteActual.setRazon_social(cliente.getRazon_social());
      clienteActual.setNombre_comercial(cliente.getNombre_comercial());
      clienteActual.setRuc(cliente.getRuc());
      clienteActual.setDv(cliente.getDv());
      clienteActual.setDireccion(cliente.getDireccion());
      clienteActual.setTelefono_jefe(cliente.getTelefono_jefe());
      clienteActual.setCelular_jefe(cliente.getCelular_jefe());
      clienteActual.setCorreo_electronico(cliente.getCorreo_electronico());
      clienteActual.setActividad_economica(cliente.getActividad_economica());
      clienteActual.setAbreviatura(cliente.getAbreviatura());
      clienteActual.setNombre_contacto(cliente.getNombre_contacto());
      clienteActual.setCargo_servicio(cliente.getCargo_servicio());
      clienteActual.setCelular_servicio(cliente.getCelular_servicio());
      clienteActual.setCorreo_servicio(cliente.getCorreo_servicio());
      clienteActual.setTelefono_servicio(cliente.getTelefono_servicio());
      clienteActual.setNombre_cobro(cliente.getNombre_cobro());
      clienteActual.setCargo_cobro(cliente.getCargo_cobro());
      clienteActual.setTelefono_cobro(cliente.getTelefono_cobro());
      clienteActual.setCelular_cobro(cliente.getCelular_cobro());
      clienteActual.setCorreo_cobro(cliente.getCorreo_cobro());
      clienteUpdated = clienteService.save(clienteActual);

    } catch (DataAccessException e) {
      response.put("mensaje", "Error al actualizar el cliente en la base de datos!");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    response.put("mensaje", "El cliente ha sido actualizado con éxito!");
    response.put("cliente", clienteUpdated);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }

  // Borrar Cliente Por ID
  @DeleteMapping("/clientes/{id}")

  public ResponseEntity<?> delete(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    try {
      clienteService.delete(id);
    } catch (DataAccessException e) {
      if(clienteService ==null) {
        response.put("mensaje", "Error al eliminar el cliente de la base de datos!");
        response.put("mensaje", "Error:no se pudo eliminar, el cliente ID: ".concat(" no existe en la base de datos!"));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


    response.put("mensaje", "El cliente eliminado con éxito!");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }


}
