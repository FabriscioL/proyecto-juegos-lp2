package com.juegos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.juegos.model.Producto;
import com.juegos.repository.ProductoRepository;

@Service 
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> listarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoria(idCategoria);
    }
  
    public List<Producto> listarPorStockMenor(Integer stock) {
        return productoRepository.findByStockLessThanEqual(stock);
    }
}