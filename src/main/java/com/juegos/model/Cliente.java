package com.juegos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotBlank
    @Size(max = 255)
    private String nombres;

    @NotBlank
    @Size(max = 255)
    private String apellidos;

    @NotBlank
    @Email
    @Size(max = 255)
    private String correo;

    @NotBlank
    @Size(max = 255)
    private String password;

    @NotBlank
    @Size(max = 255)
    private String telefono;

    @Size(max = 255)
    private String direccion;
}