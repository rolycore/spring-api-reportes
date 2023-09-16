package com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.service;

import com.sstproyects.springboot.backend.apirest.excepciones.ReportNotFoundException;
import com.sstproyects.springboot.backend.apirest.models.dao.serviciocliente.IReporteTecnicoDao;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.Cliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.EquipoCliente;
import com.sstproyects.springboot.backend.apirest.models.entity.serviciocliente.ReporteTecnico;
import com.sstproyects.springboot.backend.apirest.models.services.serviciocliente.interzas.IReporteTecnicoService;

import com.sstproyects.springboot.backend.apirest.utileria.commons.JasperReportManager;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ReporteTecnicoServiceImpl implements IReporteTecnicoService {
  @Autowired
  private JasperReportManager reportManager;
  @Autowired
  private IReporteTecnicoDao iReporteTecnicoDao;

  @Override
  @Transactional(readOnly = true)
  public List<ReporteTecnico> findAll() {
    return iReporteTecnicoDao.findAll();
  }

  @Override
  @Transactional
  public ReporteTecnico findById(Long Idreptec) {
    return iReporteTecnicoDao.findById(Idreptec).orElse(null);
  }

  @Override
  @Transactional
  public ReporteTecnico save(ReporteTecnico reporteTecnico) {
    return iReporteTecnicoDao.save(reporteTecnico);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    iReporteTecnicoDao.deleteById(id);
  }

  public byte[] exportReport(String reportFormat) throws JRException, FileNotFoundException {

   ReporteTecnico reporteTecnico = new ReporteTecnico();

    if (reporteTecnico != null) {

      reporteTecnico = iReporteTecnicoDao.findById(reporteTecnico.getIdreptec()).orElse(null);
      Cliente cliente = reporteTecnico.getCliente();
      EquipoCliente equipoCliente = reporteTecnico.getEquipo();
      Long idCliente = (cliente != null) ? cliente.getIdCliente() : null;
      Long idEquipo = (equipoCliente != null) ? equipoCliente.getIdEquipo() : null;

      // Obtiene los datos que se utilizarán para generar el informe
      String path = "C:\\sst proyectos spring\\spring-boot-backend-api-rest\\src\\main\\resources";
      List<ReporteTecnico> reporteTecnicos = iReporteTecnicoDao.findAll();

      // Carga el archivo de diseño del informe
      File file = ResourceUtils.getFile("classpath:reporte_de_tecnico.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
      JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reporteTecnicos);

      // Define los parámetros del informe
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("cliente", idCliente);
      parameters.put("equipo", idEquipo);
      parameters.put("no_reporte_tecnico", reporteTecnico.getNo_reporte_tecnico());
      parameters.put("no_cotizacion", reporteTecnico.getNo_cotizacion());
      parameters.put("tecnico", reporteTecnico.getTecnico());
      parameters.put("horaentrada", reporteTecnico.getHoraentrada());
      parameters.put("horasalida", reporteTecnico.getHorasalida());
      parameters.put("horaviajes", reporteTecnico.getHoraviajes());
      parameters.put("fechareporte", reporteTecnico.getFechareporte() );
      parameters.put("contacto", reporteTecnico.getContacto() );
      parameters.put("direccion", reporteTecnico.getDireccion());
      parameters.put("marca", reporteTecnico.getMarca());
      parameters.put("modelo", reporteTecnico.getModelo());
      parameters.put("no_serie", reporteTecnico.getNo_serie());
      parameters.put("ubicacion_equipo", reporteTecnico.getUbicacion_equipo() );
      parameters.put("idinterno", reporteTecnico.getIdinterno());
      parameters.put("capacidad", reporteTecnico.getCapacidad());
      parameters.put("resolucion", reporteTecnico.getResolucion());
      parameters.put("calibracion", reporteTecnico.isCalibracion());
      parameters.put("instalacion", reporteTecnico.isInstalacion());
      parameters.put("verificacion", reporteTecnico.isVerificacion());
      parameters.put("entregaequipo", reporteTecnico.isEntregaequipo());
      parameters.put("gestionmetrologica", reporteTecnico.isGestionmetrologica());
      parameters.put("retiroequipo", reporteTecnico.isRetiroequipo());
      parameters.put("inspeccion", reporteTecnico.isInspeccion());
      parameters.put("otros", reporteTecnico.isOtros());
      parameters.put("observaciones", reporteTecnico.getObservaciones());
      parameters.put("desnivel", reporteTecnico.isDesnivel());
      parameters.put("vibraciones", reporteTecnico.isVibraciones());
      parameters.put("averias", reporteTecnico.isAverias());
      parameters.put("erroresindicador", reporteTecnico.isErroresindicador());
      parameters.put("soporteinadecuadas", reporteTecnico.isSoporteinadecuadas() );
      parameters.put("faltacomponente", reporteTecnico.isFaltacomponente() );
      parameters.put("suceidad", reporteTecnico.isSuceidad() );
      parameters.put("corrienteaire", reporteTecnico.isCorrienteaire() );
      parameters.put("insectos", reporteTecnico.isInsectos());
      parameters.put("golpe", reporteTecnico.isGolpe());
      parameters.put("fuentexternacalor", reporteTecnico.isFuentexternacalor() );
      parameters.put("configuracion", reporteTecnico.isConfiguracion() );
      parameters.put("observaciones2", reporteTecnico.getObservaciones2() );
      parameters.put("limpieza", reporteTecnico.isLimpieza());
      parameters.put("ajusteslinealidad", reporteTecnico.isAjusteslinealidad() );
      parameters.put("configuracion1", reporteTecnico.isConfiguracion1());
      parameters.put("ajusteexcentricidad", reporteTecnico.isAjusteexcentricidad() );
      parameters.put("observaciones3", reporteTecnico.getObservaciones3() );
      parameters.put("completo", reporteTecnico.isCompleto() );
      parameters.put("incompleto", reporteTecnico.isIncompleto() );
      parameters.put("observaciones4", reporteTecnico.getObservaciones4() );
      parameters.put("nota", reporteTecnico.getNota() );
      parameters.put("recibidopor", reporteTecnico.getRecibidopor() );
      parameters.put("fecha", reporteTecnico.getFecha());
      // Agrega el resto de los parámetros aquí...

      // Genera el informe en memoria
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

      // Exporta el informe al formato especificado
      byte[] reportBytes = null;
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


      return reportBytes;
    } else {
      // Manejo de la situación en la que no se encontró el reporte
      System.out.println("No se encontró el reporte técnico con ID " + reporteTecnico.getIdreptec());
      throw new ReportNotFoundException("No se encontró el reporte técnico con ID " + reporteTecnico.getIdreptec());
    }
  }

  @Override
  public ReporteTecnico createOrUpdate(ReporteTecnico reporteTecnico) {
    // Si el reporte tiene un ID válido, intenta recuperarlo
    ReporteTecnico existingReportetec = new ReporteTecnico();
    if (reporteTecnico.getIdreptec() != null) {
     existingReportetec = iReporteTecnicoDao.findById(reporteTecnico.getIdreptec()).orElse(null);
      if (existingReportetec != null) {
        // Actualiza los campos relevantes del EquipoCliente existente si es necesario
        existingReportetec.setActivo(reporteTecnico.isActivo());//Desactivar reporte
        existingReportetec.setCliente(reporteTecnico.getCliente());
        existingReportetec.setEquipo(reporteTecnico.getEquipo());
        existingReportetec.setTecnico(reporteTecnico.getTecnico());
        existingReportetec.setNo_cotizacion(reporteTecnico.getNo_cotizacion());
        existingReportetec.setHoraentrada(reporteTecnico.getHoraentrada());
        existingReportetec.setHorasalida(reporteTecnico.getHorasalida());
        existingReportetec.setHoraviajes(reporteTecnico.getHoraviajes());
      /*  existingReportetec.set(reporteTecnico.getFechareporte() );
        existingReportetec.set("contacto", reporteTecnico.getContacto() );
        existingReportetec.set("direccion", reporteTecnico.getDireccion());
        existingReportetec.set("marca", reporteTecnico.getMarca());
        existingReportetec.set("modelo", reporteTecnico.getModelo());
        existingReportetec.set("no_serie", reporteTecnico.getNo_serie());
        existingReportetec.set("ubicacion_equipo", reporteTecnico.getUbicacion_equipo() );
        existingReportetec.set("idinterno", reporteTecnico.getIdinterno());
        existingReportetec.set("capacidad", reporteTecnico.getCapacidad());
        existingReportetec.set("resolucion", reporteTecnico.getResolucion());
        existingReportetec.set("calibracion", reporteTecnico.isCalibracion());
        existingReportetec.set("instalacion", reporteTecnico.isInstalacion());
        existingReportetec.set("verificacion", reporteTecnico.isVerificacion());
        existingReportetec.set("entregaequipo", reporteTecnico.isEntregaequipo());
        existingReportetec.set("gestionmetrologica", reporteTecnico.isGestionmetrologica());
        existingReportetec.set("retiroequipo", reporteTecnico.isRetiroequipo());
        existingReportetec.set("inspeccion", reporteTecnico.isInspeccion());
        existingReportetec.set("otros", reporteTecnico.isOtros());
        existingReportetec.set("observaciones", reporteTecnico.getObservaciones());
        existingReportetec.set("desnivel", reporteTecnico.isDesnivel());
        existingReportetec.set("vibraciones", reporteTecnico.isVibraciones());
        existingReportetec.set("averias", reporteTecnico.isAverias());
        existingReportetec.set("erroresindicador", reporteTecnico.isErroresindicador());
        existingReportetec.set("soporteinadecuadas", reporteTecnico.isSoporteinadecuadas() );
        existingReportetec.setFaltacomponente(reporteTecnico.isFaltacomponente() );
        existingReportetec.setSuceidad(reporteTecnico.isSuceidad() );
        existingReportetec.setCorrienteaire(reporteTecnico.isCorrienteaire() );
        existingReportetec.setInsectos(reporteTecnico.isInsectos());
        existingReportetec.setGolpe(reporteTecnico.isGolpe());
        existingReportetec.setFuentexternacalor(reporteTecnico.isFuentexternacalor() );
        existingReportetec.setConfiguracion(reporteTecnico.isConfiguracion() );
        existingReportetec.setObservaciones2(reporteTecnico.getObservaciones2() );
        existingReportetec.setLimpieza(reporteTecnico.isLimpieza());
        existingReportetec.setAjusteslinealidad(reporteTecnico.isAjusteslinealidad() );
        existingReportetec.setConfiguracion1(reporteTecnico.isConfiguracion1());
        existingReportetec.setAjusteexcentricidad(reporteTecnico.isAjusteexcentricidad() );
        existingReportetec.setObservaciones3(reporteTecnico.getObservaciones3() );
        existingReportetec.setCompleto(reporteTecnico.isCompleto() );
        existingReportetec.setIncompleto(reporteTecnico.isIncompleto() );
        existingReportetec.setObservaciones4(reporteTecnico.getObservaciones4() );
        existingReportetec.setNota(reporteTecnico.getNota() );
        existingReportetec.setRecibidopor(reporteTecnico.getRecibidopor() );
        existingReportetec.setFecha(reporteTecnico.getFecha());*/
        // Actualiza otros campos según tus necesidades
        // ...

        // Guarda el reporte actualizado en la base de datos
        return iReporteTecnicoDao.save(existingReportetec);
      }
    }

    // Si el reporte no tiene un ID válido o no existe, crea uno nuevo
    return iReporteTecnicoDao.save(reporteTecnico);
  }
  }

