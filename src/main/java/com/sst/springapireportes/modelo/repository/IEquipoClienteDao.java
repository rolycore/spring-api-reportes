package com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.EquipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEquipoClienteDao extends JpaRepository<EquipoCliente, Long> {
}
