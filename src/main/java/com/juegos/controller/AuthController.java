package com.juegos.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juegos.dto.LoginDto;
import com.juegos.dto.RegistroClienteDto;
import com.juegos.model.Cliente;
import com.juegos.service.ClienteService;

@Controller
public class AuthController {

    private final ClienteService clienteService;

    public AuthController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/productos";
        }
        if (!model.containsAttribute("loginDto")) {
            model.addAttribute("loginDto", new LoginDto());
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                                BindingResult bindingResult,
                                HttpSession session,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        Cliente cliente = clienteService.validarLogin(loginDto.getCorreo().trim(), loginDto.getPassword());
        if (cliente == null) {
            model.addAttribute("errorLogin", "Credenciales incorrectas o usuario inactivo.");
            return "auth/login";
        }

        session.setAttribute("usuarioLogueado", cliente);
        session.setAttribute("usuarioNombre", cliente.getNombres() + " " + cliente.getApellidos());
        session.setAttribute("usuarioRol", cliente.getRol());
        session.setAttribute("usuarioCorreo", cliente.getCorreo());

        return "redirect:/productos";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/productos";
        }
        if (!model.containsAttribute("registroDto")) {
            model.addAttribute("registroDto", new RegistroClienteDto());
        }
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDto") RegistroClienteDto registroDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (!registroDto.getPassword().equals(registroDto.getConfirmarPassword())) {
            bindingResult.rejectValue("confirmarPassword", "error.confirmarPassword", "Las contraseñas no coinciden");
        }

        if (clienteService.existePorCorreo(registroDto.getCorreo().trim())) {
            bindingResult.rejectValue("correo", "error.correo", "El correo ya se encuentra registrado");
        }

        if (bindingResult.hasErrors()) {
            return "auth/registro";
        }

        Cliente cliente = new Cliente();
        cliente.setNombres(registroDto.getNombres().trim());
        cliente.setApellidos(registroDto.getApellidos().trim());
        cliente.setCorreo(registroDto.getCorreo().trim().toLowerCase());
        cliente.setPassword(registroDto.getPassword());
        cliente.setTelefono(registroDto.getTelefono().trim());
        cliente.setDireccion(registroDto.getDireccion() != null ? registroDto.getDireccion().trim() : "");
        cliente.setRol("CLIENTE");
        cliente.setActivo(true);

        clienteService.guardarCliente(cliente);

        redirectAttributes.addFlashAttribute("mensajeExito", "¡Cuenta creada exitosamente! Por favor inicia sesión.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensajeExito", "Sesión cerrada correctamente.");
        return "redirect:/login";
    }
}
