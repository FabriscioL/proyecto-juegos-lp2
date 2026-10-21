package com.juegos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;
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
        ClassPathResource logoResource = new ClassPathResource("static/img/logo.jpeg");

        try (InputStream reporteStream = plantilla.getInputStream()) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Reporte de Usuarios y Clientes");
            if (logoResource.exists()) {
                parametros.put("logoEmpresa", logoResource.getInputStream());
            }

            byte[] pdf = reporteService.generarReportePdf(
                    reporteStream,
                    clienteService.listarClientes(),
                    parametros);

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
    public String guardarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                                 BindingResult bindingResult,
                                 RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "clientes/formulario_cliente";
        }
        
        // Validación de correo único al crear
        if (cliente.getIdCliente() == null && clienteService.existePorCorreo(cliente.getCorreo())) {
            bindingResult.rejectValue("correo", "error.correo", "El correo ya está registrado en el sistema");
            return "clientes/formulario_cliente";
        }

        try {
            if (cliente.getRol() == null || cliente.getRol().trim().isEmpty()) {
                cliente.setRol("CLIENTE");
            }
            cliente.setActivo(true);
            clienteService.guardarCliente(cliente);
            attributes.addFlashAttribute("mensajeExito", "¡Usuario/Cliente guardado con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "Error al guardar el usuario/cliente.");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "clientes/formulario_cliente";
        } else {
            attributes.addFlashAttribute("mensajeError", "El usuario/cliente no existe.");
            return "redirect:/clientes";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id, HttpSession session, RedirectAttributes attributes) {
        try {
            Cliente usuarioLogueado = (Cliente) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado != null && usuarioLogueado.getIdCliente().equals(id)) {
                attributes.addFlashAttribute("mensajeError", "No puedes desactivar tu propia cuenta en sesión.");
                return "redirect:/clientes";
            }
            clienteService.eliminarCliente(id);
            attributes.addFlashAttribute("mensajeExito", "¡Usuario/Cliente eliminado lógicamente con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensajeError", "No se pudo eliminar el usuario/cliente.");
        }
        return "redirect:/clientes";
    }

    @PostMapping("/cambiar-rol/{id}")
    public String cambiarRol(@PathVariable("id") Long id,
                             @RequestParam("rol") String nuevoRol,
                             HttpSession session,
                             RedirectAttributes attributes) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            Cliente usuarioLogueado = (Cliente) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado != null && usuarioLogueado.getIdCliente().equals(id) && !"ADMIN".equalsIgnoreCase(nuevoRol)) {
                attributes.addFlashAttribute("mensajeError", "No puedes quitarte el rol de Administrador a ti mismo.");
                return "redirect:/clientes";
            }

            cliente.setRol(nuevoRol);
            clienteService.guardarCliente(cliente);
            attributes.addFlashAttribute("mensajeExito", "¡Rol actualizado con éxito a: " + nuevoRol + "!");
        } else {
            attributes.addFlashAttribute("mensajeError", "Usuario no encontrado.");
        }
        return "redirect:/clientes";
    }
}
