package com.juegos.service;

import com.juegos.model.Proveedor;
import com.juegos.repository.ProveedorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findByActivoTrue();
    }

    @Override
    public void guardarProveedor(Proveedor proveedor) {
        if (proveedor.getActivo() == null) {
            proveedor.setActivo(true);
        }
        proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findByIdProveedorAndActivoTrue(id).orElse(null);
    }

    @Override
    public void eliminarProveedor(Integer id) {
        proveedorRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            proveedorRepository.save(p);
        });
    }
}
