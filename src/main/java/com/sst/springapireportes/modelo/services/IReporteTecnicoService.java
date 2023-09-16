package com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas;

import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.EquipoCliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.ReporteTecnico;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;
public interface IReporteTecnicoService {
  List<ReporteTecnico> findAll();
  ReporteTecnico findById(Long id);
  ReporteTecnico save(ReporteTecnico reporteTecnico);
  void deleteById(Long id);

  byte[] exportReport(String reportFormat) throws FileNotFoundException, JRException;
  ReporteTecnico createOrUpdate(ReporteTecnico reporteTecnico);
}
