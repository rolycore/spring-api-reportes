package com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.OrdenTrabajo;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.ReporteTecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReporteTecnicoDao extends JpaRepository<ReporteTecnico, Long> {


}
