package com.juegos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juegos.model.Producto;
import com.juegos.service.CategoriaService;
import com.juegos.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "lista"; 
    }

    @GetMapping("/filtrar")
    public String filtrarProductos(@RequestParam("idCategoria") Integer idCategoria, Model model) {
        model.addAttribute("productos", productoService.listarPorCategoria(idCategoria));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "lista"; 
    }

    @GetMapping("/filtrarStock")
    public String filtrarPorStock(@RequestParam("stock") Integer stock, Model model) {
        model.addAttribute("productos", productoService.listarPorStockMenor(stock));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "lista"; 
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarCategorias()); 
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes attributes) {
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
            return "formulario"; 
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