package com.juegos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juegos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);
    
    List<Producto> findByStockLessThanEqual(Integer stock);
}