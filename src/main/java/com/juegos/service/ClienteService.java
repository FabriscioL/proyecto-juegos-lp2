package com.juegos.service;

import com.juegos.model.Cliente;
import java.util.List;

public interface ClienteService {

    List<Cliente> listarClientes();

    List<Cliente> listarInactivos();

    Cliente guardarCliente(Cliente cliente);

    Cliente buscarPorId(Long id);

    void eliminarCliente(Long id);

    void restaurarCliente(Long id);

    Cliente validarLogin(String correo, String password);

    boolean existePorCorreo(String correo);

    Cliente buscarPorCorreo(String correo);

    long contarActivos();

    long contarActivosPorRol(String rol);
}
