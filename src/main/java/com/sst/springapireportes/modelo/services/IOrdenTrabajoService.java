package com.sst.springapireportes.modelo.services;

import com.sst.springapireportes.modelo.entidad.OrdenTrabajo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface IOrdenTrabajoService {
  List<OrdenTrabajo> findAll(Pageable pageable);
  Optional<OrdenTrabajo> findById(Long idOT);
 //OrdenTrabajo findById(Long idOT);
  OrdenTrabajo save(OrdenTrabajo ordenTrabajo);
  boolean delete(Long idOT);

  // Agregamos un método para crear un nuevo cliente o recuperar uno existente por su identificador
  OrdenTrabajo createOrUpdate(OrdenTrabajo ordenTrabajo);
  byte[] exportOtPdf(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException;
 // byte[] exportXls(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException;
}
