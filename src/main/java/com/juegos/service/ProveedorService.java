package com.juegos.service;

import java.util.List;
import com.juegos.model.Proveedor;

public interface ProveedorService {

    List<Proveedor> listarProveedores();

    void guardarProveedor(Proveedor proveedor);

    Proveedor buscarPorId(Integer id);

    void eliminarProveedor(Integer id);
}