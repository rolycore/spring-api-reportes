package com.sst.springapireportes.modelo.impl;


import com.sst.springapireportes.modelo.entidad.OrdenTrabajo;
import com.sst.springapireportes.modelo.repository.IOrdenTrabajoDao;
import com.sst.springapireportes.modelo.services.IOrdenTrabajoService;
import com.sst.springapireportes.util.OrdenTrabajoReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenTrabajoServiceImpl implements IOrdenTrabajoService {
  @Autowired
  private IOrdenTrabajoDao ordenTrabajoDao;
  @Autowired
  private OrdenTrabajoReportGenerator ordenTrabajoReportGenerator;
  @Override
  public List<OrdenTrabajo> findAll(Pageable pageable) {
    return (List<OrdenTrabajo>) ordenTrabajoDao.findAll();
  }

  @Override
  @Transactional (readOnly = true)
  public Optional<OrdenTrabajo> findById(Long idOT) {

    return ordenTrabajoDao.findById(idOT);
  }

  @Override
  public OrdenTrabajo save(OrdenTrabajo ordenTrabajo) {
    return ordenTrabajoDao.save(ordenTrabajo);
  }

  @Override
  public boolean delete(Long idOT) {
    try {
      ordenTrabajoDao.deleteById(idOT); // Esto eliminará el cliente por ID si existe
      return true; // Devuelve true para indicar éxito
    } catch (Exception e) {
      // En caso de error, puedes registrar el error o realizar otro manejo
      return false; // Devuelve false para indicar que no se pudo eliminar
    }
  }

  @Override
  public OrdenTrabajo createOrUpdate(OrdenTrabajo ordenTrabajo) {
    // Si el cliente tiene un ID válido, intenta recuperarlo
    if (ordenTrabajo.getIdOT() != null) {
      OrdenTrabajo existingOT = ordenTrabajoDao.findById(ordenTrabajo.getIdOT()).orElse(null);
      if (existingOT != null) {
        // Actualiza los campos relevantes del cliente existente si es necesario
        existingOT.setCliente(ordenTrabajo.getCliente());
        existingOT.setEquipo(ordenTrabajo.getEquipo());
        // Actualiza otros campos según tus necesidades
        // ...
      }
    }
    // Guarda el cliente actualizado en la base de datos
    return ordenTrabajoDao.save(ordenTrabajo);
  }

  @Override
  public byte[] exportOtPdf(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException {
    // Llama al método exportToPdf con el ID de la OrdenTrabajo
    return ordenTrabajoReportGenerator.exportToPdf(ordenTrabajo.getIdOT());
  }


 /* @Override
  public byte[] exportXls(List<OrdenTrabajo> ordenTrabajo) throws JRException, FileNotFoundException {
    return ordenTrabajoReportGenerator.exportToXls(ordenTrabajo);
  }*/


}
