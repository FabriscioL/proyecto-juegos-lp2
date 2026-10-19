package com.juegos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import com.juegos.model.Proveedor;
import com.juegos.service.ProveedorService;
import com.juegos.service.ReporteService;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProveedorController.class);

    private final ProveedorService proveedorService;

    private final ReporteService reporteService;

    public ProveedorController(ProveedorService proveedorService, ReporteService reporteService) {
        this.proveedorService = proveedorService;
        this.reporteService = reporteService;
    }

    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        return "proveedores/proveedor";
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteProveedoresPdf() {
        ClassPathResource plantilla = new ClassPathResource("reports/proveedores_reporte.jrxml");
        try (InputStream reporteStream = plantilla.getInputStream()) {
            byte[] pdf = reporteService.generarReportePdf(
                    reporteStream,
                    proveedorService.listarProveedores(),
                    Map.of("titulo", "Directorio de proveedores"));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=proveedores.pdf")
                    .body(pdf);
        } catch (IOException | IllegalStateException e) {
            LOGGER.error("No se pudo generar el reporte PDF de proveedores", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/formulario_proveedor";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@Valid @ModelAttribute Proveedor proveedor, BindingResult bindingResult,
            RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "proveedores/formulario_proveedor";
        }
        try {
            proveedorService.guardarProveedor(proveedor);
            attributes.addFlashAttribute("mensajeExito", "¡Proveedor guardado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "Error al guardar el proveedor.");
        }
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        Proveedor proveedor = proveedorService.buscarPorId(id);
        if (proveedor != null) {
            model.addAttribute("proveedor", proveedor);
            return "proveedores/formulario_proveedor";
        } else {
            attributes.addFlashAttribute("mensajeError", "El proveedor no existe.");
            return "redirect:/proveedores";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            proveedorService.eliminarProveedor(id);
            attributes.addFlashAttribute("mensajeExito", "¡Proveedor eliminado correctamente!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "No se pudo eliminar el proveedor.");
        }
        return "redirect:/proveedores";
    }
}