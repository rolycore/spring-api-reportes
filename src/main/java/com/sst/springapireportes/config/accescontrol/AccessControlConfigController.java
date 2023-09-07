package com.sst.springapireportes.config.accescontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/access-control-configs")
@PreAuthorize("hasRole('ADMIN')")
public class AccessControlConfigController {

  private final AccessControlConfigService accessControlConfigService;

  @Autowired
  public AccessControlConfigController(AccessControlConfigService accessControlConfigService) {
    this.accessControlConfigService = accessControlConfigService;
  }

  // Endpoint para obtener todas las configuraciones de control de acceso
  @GetMapping
  public ResponseEntity<List<AccessControlConfig>> getAllAccessControlConfigs() {
    List<AccessControlConfig> configs = accessControlConfigService.getAllConfigs();
    return ResponseEntity.ok(configs);
  }

  // Endpoint para crear una nueva configuración de control de acceso
  @PostMapping
  public ResponseEntity<AccessControlConfig> createAccessControlConfig(@RequestBody AccessControlConfig config) {
    AccessControlConfig createdConfig = accessControlConfigService.createConfig(config);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdConfig);
  }

  // Endpoint para actualizar una configuración de control de acceso existente
  @PutMapping("/{id}")
  public ResponseEntity<AccessControlConfig> updateAccessControlConfig(
    @PathVariable Long id, @RequestBody AccessControlConfig updatedConfig) {
    AccessControlConfig config = accessControlConfigService.updateConfig(id, updatedConfig);
    return ResponseEntity.ok(config);
  }

  // Endpoint para eliminar una configuración de control de acceso
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccessControlConfig(@PathVariable Long id) {
    accessControlConfigService.deleteConfig(id);
    return ResponseEntity.noContent().build();
  }
}
