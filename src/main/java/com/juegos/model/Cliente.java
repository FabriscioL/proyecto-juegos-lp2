package com.juegos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
    private String telefono;
    private String direccion;
}