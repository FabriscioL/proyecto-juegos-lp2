package com.juegos.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Override
    public byte[] generarReportePdf(InputStream reporteStream, List<?> datos, Map<String, Object> parametros) {
        try {
            JasperReport reporteCompilado = JasperCompileManager.compileReport(reporteStream);
            JRBeanCollectionDataSource fuenteDatos = new JRBeanCollectionDataSource(datos);
            Map<String, Object> parametrosMutables = parametros == null ? new HashMap<>() : new HashMap<>(parametros);
            JasperPrint reporteLleno = JasperFillManager.fillReport(reporteCompilado, parametrosMutables, fuenteDatos);
            return JasperExportManager.exportReportToPdf(reporteLleno);
        } catch (JRException e) {
            throw new IllegalStateException("No se pudo generar el reporte PDF", e);
        }
    }
}