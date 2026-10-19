package com.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
