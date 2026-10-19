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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import com.juegos.model.Producto;
import com.juegos.service.CategoriaService;
import com.juegos.service.ProductoService;
import com.juegos.service.ReporteService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService productoService;

    private final CategoriaService categoriaService;

    private final ReporteService reporteService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService,
            ReporteService reporteService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.reporteService = reporteService;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "productos/lista";
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteJuegosPdf() {
        ClassPathResource plantilla = new ClassPathResource("reports/juegos_reporte.jrxml");
        try (InputStream reporteStream = plantilla.getInputStream()) {
            byte[] pdf = reporteService.generarReportePdf(
                    reporteStream,
                    productoService.listarProductos(),
                    Map.of("titulo", "Catálogo de videojuegos"));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=juegos.pdf")
                    .body(pdf);
        } catch (IOException | IllegalStateException e) {
            LOGGER.error("No se pudo generar el reporte PDF de juegos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/filtrar")
    public String filtrarProductos(@RequestParam("idCategoria") Integer idCategoria, Model model) {
        model.addAttribute("productos", productoService.listarPorCategoria(idCategoria));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "productos/lista";
    }

    @GetMapping({"/filtrar-stock", "/filtrarStock"})
    public String filtrarPorStock(@RequestParam("stock") Integer stock, Model model) {
        model.addAttribute("productos", productoService.listarPorStockMenor(stock));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarCategorias()); 
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult bindingResult,
            Model model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarCategorias());
            return "productos/formulario";
        }
        try {
            productoService.guardarProducto(producto);
            attributes.addFlashAttribute("mensajeExito", "¡Producto guardado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "Error al guardar el producto.");
        }
        return "redirect:/productos";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        Producto producto = productoService.buscarPorId(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            model.addAttribute("categorias", categoriaService.listarCategorias());
            return "productos/formulario";
        } else {
            attributes.addFlashAttribute("mensajeError", "El producto no existe.");
            return "redirect:/productos";
        }
    } 

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            productoService.eliminarProducto(id);
            attributes.addFlashAttribute("mensajeExito", "¡Producto eliminado correctamente!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "No se pudo eliminar el producto.");
        }
        return "redirect:/productos";
    }
}