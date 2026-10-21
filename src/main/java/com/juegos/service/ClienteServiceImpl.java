package com.juegos.service;

import com.juegos.model.Cliente;
import com.juegos.repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findByActivoTrue();
    }

    @Override
    public List<Cliente> listarInactivos() {
        return clienteRepository.findByActivoFalse();
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        if (cliente.getActivo() == null) {
            cliente.setActivo(true);
        }
        if (cliente.getRol() == null || cliente.getRol().trim().isEmpty()) {
            cliente.setRol("CLIENTE");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findByIdClienteAndActivoTrue(id).orElse(null);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.findById(id).ifPresent(c -> {
            c.setActivo(false);
            clienteRepository.save(c);
        });
    }

    @Override
    public void restaurarCliente(Long id) {
        clienteRepository.findById(id).ifPresent(c -> {
            c.setActivo(true);
            clienteRepository.save(c);
        });
    }

    @Override
    public Cliente validarLogin(String correo, String password) {
        Cliente cliente = clienteRepository.findByCorreoAndActivoTrue(correo);
        if (cliente != null && cliente.getPassword() != null && cliente.getPassword().equals(password)) {
            return cliente;
        }
        return null;
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return clienteRepository.existsByCorreo(correo);
    }

    @Override
    public Cliente buscarPorCorreo(String correo) {
        return clienteRepository.findByCorreo(correo);
    }

    @Override
    public long contarActivos() {
        return clienteRepository.countByActivoTrue();
    }

    @Override
    public long contarActivosPorRol(String rol) {
        return clienteRepository.countByActivoTrueAndRol(rol);
    }
}
