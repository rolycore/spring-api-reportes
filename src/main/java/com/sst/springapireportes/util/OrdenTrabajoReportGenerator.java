package com.sst.springapireportes.util;
import com.sst.springapireportes.excepciones.ReportNotFoundException;
import com.sst.springapireportes.modelo.entidad.OrdenTrabajo;
import com.sst.springapireportes.modelo.services.IOrdenTrabajoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class OrdenTrabajoReportGenerator {

    private IOrdenTrabajoService ordenTrabajoService;
    public byte[] exportToPdf(Long idOT) throws JRException, FileNotFoundException {
        Optional<OrdenTrabajo> ordenTrabajoOptional = ordenTrabajoService.findById(idOT);
        if (ordenTrabajoOptional.isPresent()) {
            OrdenTrabajo ordenTrabajo = ordenTrabajoOptional.get();
            List<OrdenTrabajo> ordenTrabajoList = Collections.singletonList(ordenTrabajo);
            return JasperExportManager.exportReportToPdf(getReport(ordenTrabajoList));
        } else {
            // Manejo de la situación en la que no se encuentra una Orden de trabajo con el ID proporcionado
            throw new ReportNotFoundException("No se encontró una Orden de trabajo con el ID " + idOT);
        }
    }


    public byte[] exportToXls(List<OrdenTrabajo> ordenTrabajo) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport((List<OrdenTrabajo>) ordenTrabajo)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<OrdenTrabajo> ordenesTrabajo) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nombre_cliente", new JRBeanCollectionDataSource((Collection<?>) ordenesTrabajo));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:reporte_de_trabajo.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }
}
