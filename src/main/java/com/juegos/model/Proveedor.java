package com.juegos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proveedor")
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String contacto;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String correo;
}