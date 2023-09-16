package com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenTrabajoDao extends JpaRepository <OrdenTrabajo, Long> {
}
