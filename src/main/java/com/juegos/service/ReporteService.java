package com.juegos.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ReporteService {

    byte[] generarReportePdf(InputStream reporteStream, List<?> datos, Map<String, Object> parametros);
}