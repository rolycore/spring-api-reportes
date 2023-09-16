package com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteDao extends JpaRepository<Cliente, Long> {


  // Otros métodos de consulta si los necesitas


}
