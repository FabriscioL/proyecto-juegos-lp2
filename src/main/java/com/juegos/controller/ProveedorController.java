package com.juegos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juegos.model.Proveedor;
import com.juegos.service.ProveedorService;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        return "proveedor"; 
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "formulario_proveedor";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor, RedirectAttributes attributes) {
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
            return "formulario_proveedor";
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