package com.juegos.service;

import java.util.List;
import com.juegos.model.Producto;

public interface ProductoService {

    List<Producto> listarProductos();

    void guardarProducto(Producto producto);

    void eliminarProducto(Integer id);

    Producto buscarPorId(Integer id);

    List<Producto> listarPorCategoria(Integer idCategoria);

    List<Producto> listarPorStockMenor(Integer stock);
}