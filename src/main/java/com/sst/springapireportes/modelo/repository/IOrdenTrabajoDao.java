package com.sst.springapireportes.modelo.repository;


import com.sst.springapireportes.modelo.entidad.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenTrabajoDao extends JpaRepository <OrdenTrabajo, Long> {
}
