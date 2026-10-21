package com.juegos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juegos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();

    List<Producto> findByActivoFalse();

    Optional<Producto> findByIdProductoAndActivoTrue(Integer idProducto);

    List<Producto> findByCategoriaIdCategoriaAndActivoTrue(Integer idCategoria);

    List<Producto> findByStockLessThanEqualAndActivoTrue(Integer stock);

    long countByActivoTrue();

    long countByActivoTrueAndStockLessThanEqual(Integer stock);
}
