package com.juegos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    List<Cliente> findByActivoTrue();

    Optional<Cliente> findByIdClienteAndActivoTrue(Long idCliente);

    Cliente findByCorreo(String correo);

    Cliente findByCorreoAndActivoTrue(String correo);

    boolean existsByCorreo(String correo);
}
