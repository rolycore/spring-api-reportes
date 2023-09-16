package com.sst.springapireportes.controllers;


import com.sst.springapireportes.modelo.entidad.OrdenTrabajo;
import com.sst.springapireportes.modelo.repository.IOrdenTrabajoDao;
import com.sst.springapireportes.modelo.services.IOrdenTrabajoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/")
@Tag(name = "OrdenesTrabajo",description = "Registro de ordenes de Trabajos")
@SecurityRequirement(name = "bearer-key")
public class OrdenTrabajoController {
  private final IOrdenTrabajoDao iOrdenTrabajoDao;

  @Autowired
  private IOrdenTrabajoService iOrdenTrabajoService;
  @Autowired
  public OrdenTrabajoController(IOrdenTrabajoDao iOrdenTrabajoDao) {
    this.iOrdenTrabajoDao = iOrdenTrabajoDao;
  }

  // Buscar todas las Ordenes de Trabajo
  @GetMapping("/ordenes-trabajos")
  public ResponseEntity<Page<OrdenTrabajo>> listarOrdenes(Pageable pageable) {
    return ResponseEntity.ok((Page<OrdenTrabajo>) iOrdenTrabajoService.findAll(pageable));

  }
  // Buscar orden de trabajo por ID
  @GetMapping("/ordenes-trabajos/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<OrdenTrabajo> getPrecioById(@PathVariable Long id) {
    OrdenTrabajo ordenTrabajo= iOrdenTrabajoDao.findById(id).orElse(null);
    if (ordenTrabajo != null) {
      return ResponseEntity.ok(ordenTrabajo);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  //Creando Ordenes de Trabajos
  @PostMapping("/ordenes-trabajos")
  public ResponseEntity<OrdenTrabajo> create(@Valid @RequestBody OrdenTrabajo ordenTrabajo, BindingResult result) throws IOException {
    Optional<OrdenTrabajo>ordenTrabajoOptional= iOrdenTrabajoService.findById(ordenTrabajo.getCliente().getIdCliente());
    if(!ordenTrabajoOptional.isPresent()){
      return ResponseEntity.unprocessableEntity().build();
    }
    ordenTrabajo.setCliente(ordenTrabajoOptional.get().getCliente());
    OrdenTrabajo createdOrdenTrabajo =  iOrdenTrabajoService.createOrUpdate(ordenTrabajo);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrdenTrabajo);
  }

  // Actualizar Orden Trabajo por ID
  @PutMapping("/ordenes-trabajos/{id}")
  public ResponseEntity<OrdenTrabajo> update(@PathVariable Long id,  @ModelAttribute OrdenTrabajo ordenTrabajo, BindingResult result) throws IOException {
    // Verifica si la OrdenTrabajo existe primero
    Optional<OrdenTrabajo> ordenTrabajoOptional = iOrdenTrabajoService.findById(id);
    if(!ordenTrabajoOptional.isPresent()){
      return ResponseEntity.unprocessableEntity().build();
    }
    ordenTrabajo.setCliente(ordenTrabajo.getCliente());
    OrdenTrabajo createdOrdenTrabajo =  iOrdenTrabajoService.createOrUpdate(ordenTrabajo);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrdenTrabajo);
  }
}
