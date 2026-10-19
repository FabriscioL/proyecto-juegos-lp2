package com.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juegos.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}