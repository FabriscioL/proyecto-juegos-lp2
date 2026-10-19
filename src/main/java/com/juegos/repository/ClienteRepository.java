package com.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juegos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    
    Cliente findByCorreo(String correo);

}