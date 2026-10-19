package com.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}