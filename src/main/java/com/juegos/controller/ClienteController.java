package com.juegos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.juegos.model.Cliente;
import com.juegos.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "cliente"; 
    }

    
    @GetMapping("/nuevo")
    public String nuevoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formulario_cliente"; 
    }

    
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    
    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "formulario_cliente";
    }

    
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
