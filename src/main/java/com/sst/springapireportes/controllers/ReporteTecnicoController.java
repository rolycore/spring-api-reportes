package com.sstproyects.springboot.backend.apirest.controllers.serviciocliente;

import com.sstproyects.springboot.backend.apirest.excepciones.ReportNotFoundException;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.EquipoCliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.ReporteTecnico;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IClienteService;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IEquipoClienteService;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IReporteTecnicoService;
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

  @GetMapping("/reporte-tecnico/{id}/{format}")
  public ResponseEntity<byte[]> generateReport(
    @PathVariable Long id, @PathVariable String format,
    @ModelAttribute ReporteTecnico reporteTecnico) {
    try {
      // Validar el formato proporcionado
      if (!isValidFormat(format)) {
        return ResponseEntity.badRequest().body("Formato no válido".getBytes());
      }

      reporteTecnico = iReporteTecnicoService.findById(id);
      if (id == null) {
        return ResponseEntity.badRequest().body("El ID no puede ser nulo ".getBytes());
      }
      if (reporteTecnico != null) {
        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteService.findById(reporteTecnico.getCliente().getIdCliente()));
        if (!clienteOptional.isPresent()) {
          return ResponseEntity.notFound().build();
        }
        reporteTecnico.setCliente(clienteOptional.get());

        Optional<EquipoCliente> equipoClienteOptional = Optional.ofNullable(equipoClienteService.findById(reporteTecnico.getEquipo().getIdEquipo()));
        if (!equipoClienteOptional.isPresent()) {
          return ResponseEntity.notFound().build();
        }
        reporteTecnico.setEquipo(equipoClienteOptional.get());

        // Generar el informe aquí con el formato especificado
        byte[] reportBytes = iReporteTecnicoService.exportReport(format);

        // Puedes guardar el informe en la base de datos aquí si es necesario

        // Devuelve el informe o lo que sea adecuado en tu caso
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getContentType(format)); // Define el tipo de contenido según el formato
        headers.add("Content-Disposition", "inline; filename=reporte." + format);

        return ResponseEntity.ok()
          .headers(headers)
          .body(reportBytes);
      } else {
        return ResponseEntity.badRequest()
          .body("No se encontró el ID del reporte".getBytes());
      }
    } catch (IOException e) {
      // Manejo de errores en caso de que la conversión falle.
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al generar el informe. ".getBytes());
    } catch (JRException e) {
      // Manejo de la situación en la que no se encontró el reporte
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("No se encontró el reporte técnico con ID " + reporteTecnico.getIdreptec());
      throw new ReportNotFoundException("No se encontró el reporte técnico con ID " + reporteTecnico.getIdreptec());
    }
  }




  // Método para validar el formato
  private boolean isValidFormat(String format) {
    // Aquí puedes agregar lógica para validar formatos permitidos, por ejemplo, "pdf", "csv", etc.
    // Devuelve true si el formato es válido, de lo contrario, false.
    return format != null && (format.equalsIgnoreCase("pdf") || format.equalsIgnoreCase("csv"));
  }

  // Método para obtener el tipo de contenido según el formato
  private MediaType getContentType(String format) {
    // Define el tipo de contenido según el formato
    if (format.equalsIgnoreCase("pdf")) {
      return MediaType.APPLICATION_PDF;
    } else {
      // Agrega más tipos de contenido si es necesario
      return MediaType.APPLICATION_OCTET_STREAM; // Tipo de contenido por defecto
    }

  }
  /*@GetMapping("/reporte-tecnico/formato/{format}")
  public Object generateReport(@PathVariable String format, @ModelAttribute ReporteTecnico reporteTecnico) throws FileNotFoundException, JRException {
    try {
      Optional<Cliente> clienteOptional = Optional.ofNullable(clienteService.findById(reporteTecnico.getCliente().getIdCliente()));
      if (!clienteOptional.isPresent()) {
        return ResponseEntity.unprocessableEntity().build();
      }
      reporteTecnico.setCliente(clienteOptional.get());
      Optional<EquipoCliente> equipoClienteOptional = Optional.ofNullable(equipoClienteService.findById(reporteTecnico.getEquipo().getIdEquipo()));
      if (!clienteOptional.isPresent()) {
        return ResponseEntity.unprocessableEntity().build();
      }
      reporteTecnico.setEquipo(equipoClienteOptional.get());
      ReporteTecnico generatedReport = iReporteTecnicoService.exportReport(String.valueOf(reporteTecnico));
      return ResponseEntity.status(HttpStatus.CREATED).body(generatedReport);

    }catch (IOException e) {
        // Manejo de errores en caso de que la conversión falle.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

  }*/
  /*@GetMapping("/export-pdf")
  public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("petsReport", "petsReport.pdf");
    return ResponseEntity.ok().headers(headers).body(iReporteTecnicoService.exportPdf());
  }

  @GetMapping("/export-xls")
  public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
    var contentDisposition = ContentDisposition.builder("attachment")
      .filename("petsReport" + ".xls").build();
    headers.setContentDisposition(contentDisposition);
    return ResponseEntity.ok()
      .headers(headers)
      .body(iReporteTecnicoService.exportXls());
  }*/
}

