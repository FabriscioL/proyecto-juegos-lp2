package com.juegos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);
    
    List<Producto> findByStockLessThanEqual(Integer stock);
}