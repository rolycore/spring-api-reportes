package com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.OrdenTrabajo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrdenTrabajoService {
  List<OrdenTrabajo> findAll(Pageable pageable);
  OrdenTrabajo findById(Long idOT);
  OrdenTrabajo save(OrdenTrabajo ordenTrabajo);
  boolean delete(Long idOT);

  // Agregamos un método para crear un nuevo cliente o recuperar uno existente por su identificador
  OrdenTrabajo createOrUpdate(OrdenTrabajo ordenTrabajo);
}
