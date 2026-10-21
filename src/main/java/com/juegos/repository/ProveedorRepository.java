package com.juegos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    List<Proveedor> findByActivoTrue();

    List<Proveedor> findByActivoFalse();

    Optional<Proveedor> findByIdProveedorAndActivoTrue(Integer idProveedor);

    long countByActivoTrue();
}
