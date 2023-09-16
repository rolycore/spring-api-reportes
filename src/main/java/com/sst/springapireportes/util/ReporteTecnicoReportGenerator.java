package com.sst.springapireportes.util;
import com.sst.springapireportes.modelo.entidad.ReporteTecnico;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteTecnicoReportGenerator {

    public byte[] exportToPdf(ReporteTecnico  reporteTecnico) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(reporteTecnico));
    }

    public byte[] exportToXls(ReporteTecnico reporteTecnico) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(reporteTecnico)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(ReporteTecnico  reporteTecnico) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("cliente", reporteTecnico.getCliente());
        params.put("equipo", reporteTecnico.getEquipo());
        params.put("no_reporte_tecnico", reporteTecnico.getNo_reporte_tecnico());
        params.put("no_cotizacion", reporteTecnico.getNo_cotizacion());
        params.put("tecnico", reporteTecnico.getTecnico());
        params.put("horaentrada", reporteTecnico.getHoraentrada());
        params.put("horasalida", reporteTecnico.getHorasalida());
        params.put("horaviajes", reporteTecnico.getHoraviajes());
        params.put("fechareporte", reporteTecnico.getFechareporte() );
        params.put("contacto", reporteTecnico.getContacto() );
        params.put("direccion", reporteTecnico.getDireccion());
        params.put("marca", reporteTecnico.getMarca());
        params.put("modelo", reporteTecnico.getModelo());
        params.put("no_serie", reporteTecnico.getNo_serie());
        params.put("ubicacion_equipo", reporteTecnico.getUbicacion_equipo() );
        params.put("idinterno", reporteTecnico.getIdinterno());
        params.put("capacidad", reporteTecnico.getCapacidad());
        params.put("resolucion", reporteTecnico.getResolucion());
        params.put("calibracion", reporteTecnico.isCalibracion());
        params.put("instalacion", reporteTecnico.isInstalacion());
        params.put("verificacion", reporteTecnico.isVerificacion());
        params.put("entregaequipo", reporteTecnico.isEntregaequipo());
        params.put("gestionmetrologica", reporteTecnico.isGestionmetrologica());
        params.put("retiroequipo", reporteTecnico.isRetiroequipo());
        params.put("inspeccion", reporteTecnico.isInspeccion());
        params.put("otros", reporteTecnico.isOtros());
        params.put("observaciones", reporteTecnico.getObservaciones());
        params.put("desnivel", reporteTecnico.isDesnivel());
        params.put("vibraciones", reporteTecnico.isVibraciones());
        params.put("averias", reporteTecnico.isAverias());
        params.put("erroresindicador", reporteTecnico.isErroresindicador());
        params.put("soporteinadecuadas", reporteTecnico.isSoporteinadecuadas() );
        params.put("faltacomponente", reporteTecnico.isFaltacomponente() );
        params.put("suceidad", reporteTecnico.isSuceidad() );
        params.put("corrienteaire", reporteTecnico.isCorrienteaire() );
        params.put("insectos", reporteTecnico.isInsectos());
        params.put("golpe", reporteTecnico.isGolpe());
        params.put("fuentexternacalor", reporteTecnico.isFuentexternacalor() );
        params.put("configuracion", reporteTecnico.isConfiguracion() );
        params.put("observaciones2", reporteTecnico.getObservaciones2() );
        params.put("limpieza", reporteTecnico.isLimpieza());
        params.put("ajusteslinealidad", reporteTecnico.isAjusteslinealidad() );
        params.put("configuracion1", reporteTecnico.isConfiguracion1());
        params.put("ajusteexcentricidad", reporteTecnico.isAjusteexcentricidad() );
        params.put("observaciones3", reporteTecnico.getObservaciones3() );
        params.put("completo", reporteTecnico.isCompleto() );
        params.put("incompleto", reporteTecnico.isIncompleto() );
        params.put("observaciones4", reporteTecnico.getObservaciones4() );
        params.put("nota", reporteTecnico.getNota() );
        params.put("recibidopor", reporteTecnico.getRecibidopor() );
        params.put("fecha", reporteTecnico.getFecha());

        //params.put("petsData", new JRBeanCollectionDataSource((Collection<?>) reporteTecnico));
        // Define los parámetros del informe
             JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:reporte_de_tecnico.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }

}
