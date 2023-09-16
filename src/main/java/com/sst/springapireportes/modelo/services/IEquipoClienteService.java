package com.sst.springapireportes.modelo.services;


import com.sst.springapireportes.modelo.entidad.EquipoCliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IEquipoClienteService {
  List<EquipoCliente> findAll();


  @Transactional
  EquipoCliente findById(Long idEquipo);

  EquipoCliente save(EquipoCliente equipocliente);


  @Transactional
  boolean delete(Long idEquipo);

  // Agregamos un método para crear un nuevo EquipoCliente o actualizar uno existente
  EquipoCliente createOrUpdate(EquipoCliente equipocliente);
}
