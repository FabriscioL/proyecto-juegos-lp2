package com.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    
    Cliente findByCorreo(String correo);

}