package com.sst.springapireportes.modelo.impl;

import com.sst.springapireportes.modelo.entidad.EquipoCliente;
import com.sst.springapireportes.modelo.repository.IEquipoClienteDao;
import com.sst.springapireportes.modelo.services.IEquipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class EquipoClienteServiceImpl implements IEquipoClienteService {
  @Autowired
  private IEquipoClienteDao equipoclienteDao;
  @Override
  @Transactional(readOnly = true)
  public List<EquipoCliente> findAll() {
    return (List<EquipoCliente>) equipoclienteDao.findAll();
  }

  @Override
  @Transactional
  public EquipoCliente findById(Long idEquipo) {
    return equipoclienteDao.findById(idEquipo).orElse(null);
  }

  @Override
  @Transactional
  public EquipoCliente save(EquipoCliente equipocliente) {
    return equipoclienteDao.save(equipocliente);
  }

  @Override
  @Transactional
  public boolean delete(Long idEquipo) {
    try {
      equipoclienteDao.deleteById(idEquipo);// Esto eliminará el cliente por ID si existe
      return true; // Devuelve true para indicar éxito
    } catch (Exception e) {
      // En caso de error, puedes registrar el error o realizar otro manejo
      return false; // Devuelve false para indicar que no se pudo eliminar
    }
  }


  @Override
  public EquipoCliente createOrUpdate(EquipoCliente equipocliente) {
    // Si el EquipoCliente tiene un ID válido, intenta recuperarlo
    if (equipocliente.getIdEquipo() != null) {
      EquipoCliente existingEquipoCliente = equipoclienteDao.findById(equipocliente.getIdEquipo()).orElse(null);
      if (existingEquipoCliente != null) {
        // Actualiza los campos relevantes del EquipoCliente existente si es necesario
        existingEquipoCliente.setNombre(equipocliente.getNombre());
        // Actualiza otros campos según tus necesidades
        // ...

        // Guarda el EquipoCliente actualizado en la base de datos
        return equipoclienteDao.save(existingEquipoCliente);
      }
    }

    // Si el EquipoCliente no tiene un ID válido o no existe, crea uno nuevo
    return equipoclienteDao.save(equipocliente);
  }
}
