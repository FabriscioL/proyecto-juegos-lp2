package com.juegos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.juegos.model.Proveedor;
import com.juegos.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public void guardarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public void eliminarProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }
}