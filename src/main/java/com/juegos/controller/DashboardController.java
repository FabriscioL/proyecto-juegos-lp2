package com.juegos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.juegos.service.CategoriaService;
import com.juegos.service.ClienteService;
import com.juegos.service.ProductoService;
import com.juegos.service.ProveedorService;

@Controller
public class DashboardController {

    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final ProveedorService proveedorService;
    private final CategoriaService categoriaService;

    public DashboardController(ProductoService productoService,
                               ClienteService clienteService,
                               ProveedorService proveedorService,
                               CategoriaService categoriaService) {
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.proveedorService = proveedorService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/dashboard")
    public String verDashboard(Model model) {
        model.addAttribute("totalProductos", productoService.contarActivos());
        model.addAttribute("bajoStock", productoService.contarConBajoStock(30));
        model.addAttribute("totalProveedores", proveedorService.contarActivos());
        model.addAttribute("totalUsuarios", clienteService.contarActivos());
        model.addAttribute("totalClientes", clienteService.contarActivosPorRol("CLIENTE"));
        model.addAttribute("totalAdmins", clienteService.contarActivosPorRol("ADMIN"));
        model.addAttribute("totalVendedores", clienteService.contarActivosPorRol("VENDEDOR"));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("productos", productoService.listarProductos());

        return "dashboard/index";
    }
}
