package com.sst.springapireportes.controllers;

import com.sst.springapireportes.excepciones.ReportNotFoundException;
import com.sst.springapireportes.modelo.entidad.Cliente;

import com.sst.springapireportes.modelo.entidad.EquipoCliente;
import com.sst.springapireportes.modelo.entidad.ReporteTecnico;

import com.sst.springapireportes.modelo.services.IClienteService;
import com.sst.springapireportes.modelo.services.IEquipoClienteService;
import com.sst.springapireportes.modelo.services.IReporteTecnicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/")
@Tag(name = "ReporteTecnico",description = "Registro de los reportes Tecnicos")
@SecurityRequirement(name = "bearer-key")
public class ReporteTecnicoController {

  @Autowired
  private IEquipoClienteService equipoClienteService;
  @Autowired
  private IClienteService clienteService;
  @Autowired
  private IReporteTecnicoService iReporteTecnicoService;

  @GetMapping("/reporte-tecnico")
  public ResponseEntity<?> listAll() {
    return ResponseEntity.ok(iReporteTecnicoService.findAll());
  }

  @GetMapping("/reporte-tecnico/{id}")
  public ResponseEntity<?> listById(@PathVariable Long id) {
    return ResponseEntity.ok(iReporteTecnicoService.findById(id));
  }

  @PostMapping("/reporte-tecnico")
  public ResponseEntity<?> create(@Valid @ModelAttribute ReporteTecnico reporteTecnico, BindingResult result) {

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

      Optional<Cliente>clienteOptional= Optional.ofNullable(clienteService.findById(reporteTecnico.getCliente().getIdCliente()));
      if(!clienteOptional.isPresent()){
        return ResponseEntity.unprocessableEntity().build();
      }
      reporteTecnico.setCliente(clienteOptional.get());
      Optional<EquipoCliente>equipoClienteOptional= Optional.ofNullable(equipoClienteService.findById(reporteTecnico.getEquipo().getIdEquipo()));
      if(!equipoClienteOptional.isPresent()){
        return ResponseEntity.unprocessableEntity().build();
      }
    ReporteTecnico createdReporte = iReporteTecnicoService.createOrUpdate(reporteTecnico);
      response.put("mensaje", "El Reporte Tecnico ha sido creado con éxito!");
      response.put("reporte tecnico ", createdReporte.getIdreptec());
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
    catch (DataAccessException e) {

      response.put("mensaje", "Error al realizar el insertar en la base de datos!");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


  }

  @DeleteMapping("/reporte-tecnico/{id}")
  public ResponseEntity<?> deleteById(@PathVariable Long id) {
    iReporteTecnicoService.deleteById(id);
    return ResponseEntity.ok(null);
  }
  @GetMapping("/export-pdf")
  public ResponseEntity<byte[]> exportPdf(ReporteTecnico reporteTecnico) throws JRException, FileNotFoundException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("reporte_de_tecnico", "reportetecnico.pdf");
    return ResponseEntity.ok().headers(headers).body(iReporteTecnicoService.exportPdf(reporteTecnico));
  }

  /*@GetMapping("/export-xls")
  public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
    var contentDisposition = ContentDisposition.builder("attachment")
            .filename("reportetecnico" + ".xls").build();
    headers.setContentDisposition(contentDisposition);
    return ResponseEntity.ok()
            .headers(headers)
            .body(iReporteTecnicoService.exportXls());
  }*/
}

