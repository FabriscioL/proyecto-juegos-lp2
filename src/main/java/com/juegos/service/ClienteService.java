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