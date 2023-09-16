package com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
  List<Cliente> findAll(Pageable pageable);
  Cliente findById(Long idCliente);
  Cliente save(Cliente cliente);
  boolean delete(Long idCliente);

  // Agregamos un método para crear un nuevo cliente o recuperar uno existente por su identificador
  Cliente createOrUpdate(Cliente cliente);

}
