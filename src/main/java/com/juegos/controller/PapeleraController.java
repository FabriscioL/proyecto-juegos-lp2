package com.juegos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juegos.service.ClienteService;
import com.juegos.service.ProductoService;
import com.juegos.service.ProveedorService;

@Controller
@RequestMapping("/papelera")
public class PapeleraController {

    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final ClienteService clienteService;

    public PapeleraController(ProductoService productoService,
                              ProveedorService proveedorService,
                              ClienteService clienteService) {
        this.productoService = productoService;
        this.proveedorService = proveedorService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String verPapelera(Model model) {
        model.addAttribute("productosInactivos", productoService.listarInactivos());
        model.addAttribute("proveedoresInactivos", proveedorService.listarInactivos());
        model.addAttribute("clientesInactivos", clienteService.listarInactivos());
        return "papelera/papelera";
    }

    @GetMapping("/restaurar-producto/{id}")
    public String restaurarProducto(@PathVariable Integer id, RedirectAttributes attributes) {
        productoService.restaurarProducto(id);
        attributes.addFlashAttribute("mensajeExito", "¡Videojuego restaurado con éxito!");
        return "redirect:/papelera";
    }

    @GetMapping("/restaurar-proveedor/{id}")
    public String restaurarProveedor(@PathVariable Integer id, RedirectAttributes attributes) {
        proveedorService.restaurarProveedor(id);
        attributes.addFlashAttribute("mensajeExito", "¡Proveedor restaurado con éxito!");
        return "redirect:/papelera";
    }

    @GetMapping("/restaurar-cliente/{id}")
    public String restaurarCliente(@PathVariable Long id, RedirectAttributes attributes) {
        clienteService.restaurarCliente(id);
        attributes.addFlashAttribute("mensajeExito", "¡Usuario/Cliente restaurado con éxito!");
        return "redirect:/papelera";
    }
}
