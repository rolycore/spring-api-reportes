package com.sst.springapireportes.modelo.impl;

import java.util.List;


import com.sst.springapireportes.modelo.entidad.Cliente;
import com.sst.springapireportes.modelo.repository.IClienteDao;
import com.sst.springapireportes.modelo.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ClienteServiceImpl implements IClienteService {
  @Autowired
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll(Pageable pageable) {

      return (List<Cliente>) clienteDao.findAll(pageable);
  }

  @Override
  public Cliente findById(Long idCliente) {
    // TODO Auto-generated method stub
    return clienteDao.findById(idCliente).orElse(null);
  }

  @Override
  public Cliente save(Cliente cliente) {
    return clienteDao.save(cliente);
  }


  @Override
  public boolean delete(Long idCliente) {
    try {
      clienteDao.deleteById(idCliente); // Esto eliminará el cliente por ID si existe
      return true; // Devuelve true para indicar éxito
    } catch (Exception e) {
      // En caso de error, puedes registrar el error o realizar otro manejo
      return false; // Devuelve false para indicar que no se pudo eliminar
    }
  }


  @Override
  public Cliente createOrUpdate(Cliente cliente) {
    // Si el cliente tiene un ID válido, intenta recuperarlo
    if (cliente.getIdCliente() != null) {
      Cliente existingCliente = clienteDao.findById(cliente.getIdCliente()).orElse(null);
      if (existingCliente != null) {
        // Actualiza los campos relevantes del cliente existente si es necesario
        existingCliente.setNombre(cliente.getNombre());
        // Actualiza otros campos según tus necesidades
        // ...

        // Guarda el cliente actualizado en la base de datos
        return clienteDao.save(existingCliente);
      }
    }

    // Si el cliente no tiene un ID válido o no existe, crea uno nuevo
    return clienteDao.save(cliente);
  }


}
