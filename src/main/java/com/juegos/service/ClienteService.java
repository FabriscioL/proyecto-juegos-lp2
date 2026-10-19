package com.juegos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juegos.model.Cliente;
import com.juegos.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    
    public Cliente validarLogin(String correo, String password) {
        Cliente cliente = clienteRepository.findByCorreo(correo);
        if (cliente != null && cliente.getPassword().equals(password)) {
            return cliente; 
        }
        return null; 
    }
}