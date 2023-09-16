package com.sst.springapireportes.modelo.services;


import com.sst.springapireportes.modelo.entidad.ReporteTecnico;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;
public interface IReporteTecnicoService {
  List<ReporteTecnico> findAll();
  ReporteTecnico findById(Long id);
  ReporteTecnico save(ReporteTecnico reporteTecnico);
  void deleteById(Long id);

 // byte[] exportReport(String reportFormat) throws FileNotFoundException, JRException;
  ReporteTecnico createOrUpdate(ReporteTecnico reporteTecnico);
  byte[] exportPdf(ReporteTecnico reporteTecnico) throws JRException, FileNotFoundException;
  //byte[] exportXls() throws JRException, FileNotFoundException;
}
