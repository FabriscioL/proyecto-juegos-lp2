package com.juegos.service;

import com.juegos.model.Producto;
import com.juegos.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    public List<Producto> listarInactivos() {
        return productoRepository.findByActivoFalse();
    }

    @Override
    public void guardarProducto(Producto producto) {
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            productoRepository.save(p);
        });
    }

    @Override
    public void restaurarProducto(Integer id) {
        productoRepository.findById(id).ifPresent(p -> {
            p.setActivo(true);
            productoRepository.save(p);
        });
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findByIdProductoAndActivoTrue(id).orElse(null);
    }

    @Override
    public List<Producto> listarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoriaAndActivoTrue(idCategoria);
    }

    @Override
    public List<Producto> listarPorStockMenor(Integer stock) {
        return productoRepository.findByStockLessThanEqualAndActivoTrue(stock);
    }

    @Override
    public long contarActivos() {
        return productoRepository.countByActivoTrue();
    }

    @Override
    public long contarConBajoStock(int umbral) {
        return productoRepository.countByActivoTrueAndStockLessThanEqual(umbral);
    }
}
