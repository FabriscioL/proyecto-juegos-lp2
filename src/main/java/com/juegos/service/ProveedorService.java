package com.juegos.service;

import com.juegos.model.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<Proveedor> listarProveedores();

    List<Proveedor> listarInactivos();

    void guardarProveedor(Proveedor proveedor);

    Proveedor buscarPorId(Integer id);

    void eliminarProveedor(Integer id);

    void restaurarProveedor(Integer id);

    long contarActivos();
}
