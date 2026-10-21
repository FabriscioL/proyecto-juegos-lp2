package com.juegos.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.juegos.model.Categoria;
import com.juegos.model.Cliente;
import com.juegos.model.Producto;
import com.juegos.model.Proveedor;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReporteServiceImplTest {

    private final ReporteService reporteService = new ReporteServiceImpl();

    @Test
    void generaPdfDeClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setNombres("Ana");
        cliente.setApellidos("Pérez");
        cliente.setCorreo("ana@example.com");
        cliente.setTelefono("5551234");
        cliente.setRol("CLIENTE");

        assertPdf("/reports/clientes_reporte.jrxml", cliente, "Listado de clientes");
    }

    @Test
    void generaPdfDeJuegos() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNombre("Aventura");

        Producto producto = new Producto();
        producto.setIdProducto(2);
        producto.setNombre("Juego de prueba");
        producto.setDescripcion("Descripción de prueba");
        producto.setPrecio(59.9);
        producto.setStock(12);
        producto.setCategoria(categoria);

        assertPdf("/reports/juegos_reporte.jrxml", producto, "Catálogo de videojuegos");
    }

    @Test
    void generaPdfDeProveedores() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(3);
        proveedor.setNombre("Proveedor de prueba");
        proveedor.setContacto("Ana Pérez");
        proveedor.setTelefono("5551234");
        proveedor.setCorreo("ana@example.com");

        assertPdf("/reports/proveedores_reporte.jrxml", proveedor, "Directorio de proveedores");
    }

    private void assertPdf(String rutaPlantilla, Object dato, String titulo) throws Exception {
        InputStream plantilla = getClass().getResourceAsStream(rutaPlantilla);
        assertNotNull(plantilla);

        byte[] pdf;
        try (plantilla) {
            Map<String, Object> params = new HashMap<>();
            params.put("titulo", titulo);
            InputStream logoStream = getClass().getResourceAsStream("/static/img/logo.jpeg");
            if (logoStream != null) {
                params.put("logoEmpresa", logoStream);
            }
            pdf = reporteService.generarReportePdf(plantilla, List.of(dato), params);
        }

        assertTrue(new String(pdf, 0, 5, StandardCharsets.US_ASCII).equals("%PDF-"));
    }
}
