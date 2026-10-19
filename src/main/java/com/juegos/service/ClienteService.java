package com.juegos.service;

import com.juegos.model.Cliente;
import java.util.List;

public interface ClienteService {

    List<Cliente> listarClientes();

    Cliente guardarCliente(Cliente cliente);

    Cliente buscarPorId(Long id);

    void eliminarCliente(Long id);

    Cliente validarLogin(String correo, String password);
}
