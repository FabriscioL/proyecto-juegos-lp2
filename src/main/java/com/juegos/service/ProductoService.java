package com.juegos.service;

import com.juegos.model.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> listarProductos();

    List<Producto> listarInactivos();

    void guardarProducto(Producto producto);

    void eliminarProducto(Integer id);

    void restaurarProducto(Integer id);

    Producto buscarPorId(Integer id);

    List<Producto> listarPorCategoria(Integer idCategoria);

    List<Producto> listarPorStockMenor(Integer stock);

    long contarActivos();

    long contarConBajoStock(int umbral);
}
