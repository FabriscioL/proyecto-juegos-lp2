package com.juegos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import com.juegos.model.Cliente;
import com.juegos.service.ClienteService;
import com.juegos.service.ReporteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    private final ReporteService reporteService;

    public ClienteController(ClienteService clienteService, ReporteService reporteService) {
        this.clienteService = clienteService;
        this.reporteService = reporteService;
    }

    
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes/cliente";
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteClientesPdf() {
        ClassPathResource plantilla = new ClassPathResource("reports/clientes_reporte.jrxml");
        try (InputStream reporteStream = plantilla.getInputStream()) {
            byte[] pdf = reporteService.generarReportePdf(
                    reporteStream,
                    clienteService.listarClientes(),
                    Map.of("titulo", "Listado de clientes"));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clientes.pdf")
                    .body(pdf);
        } catch (IOException | IllegalStateException e) {
            LOGGER.error("No se pudo generar el reporte PDF de clientes", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @GetMapping("/nuevo")
    public String nuevoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario_cliente";
    }

    
    @PostMapping("/guardar")
    public String guardarCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "clientes/formulario_cliente";
        }
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    
    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "clientes/formulario_cliente";
    }

    
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
