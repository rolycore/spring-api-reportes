package com.sst.springapireportes.config.accescontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccessControlConfigService {
  private final AccessControlConfigRepository configRepository;

  @Autowired
  public AccessControlConfigService(AccessControlConfigRepository configRepository) {
    this.configRepository = configRepository;
  }

  public List<AccessControlConfig> getAllConfigs() {
    return configRepository.findAll();
  }
  public AccessControlConfig createConfig(AccessControlConfig newConfig) {
    return configRepository.save(newConfig);
  }
  public AccessControlConfig updateConfig(Long id, AccessControlConfig updatedConfig) {
    // Verificar si la configuración existe en la base de datos
    AccessControlConfig existingConfig = configRepository.findById(id)
      .orElseThrow(() -> new NoSuchElementException("Configuración no encontrada"));

    // Actualizar la configuración con los datos proporcionados
    existingConfig.setPathPattern(updatedConfig.getPathPattern());
    existingConfig.setAccessExpression(updatedConfig.getAccessExpression());

    // Guardar la configuración actualizada en la base de datos
    return configRepository.save(existingConfig);
  }
  public void deleteConfig(Long id) {
    // Verificar si la configuración existe en la base de datos
    AccessControlConfig existingConfig = configRepository.findById(id)
      .orElseThrow(() -> new NoSuchElementException("Configuración no encontrada"));

    // Eliminar la configuración de la base de datos
    configRepository.delete(existingConfig);
  }
  // Métodos para recuperar configuraciones, por ejemplo, por ruta o cualquier otro criterio de búsqueda necesario
}
