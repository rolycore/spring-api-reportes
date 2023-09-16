package com.sst.springapireportes.modelo.services;


import com.sst.springapireportes.modelo.entidad.Cliente;
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
