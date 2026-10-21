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

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255)
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 255)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 255)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 255, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 255)
    private String telefono;

    @Size(max = 255)
    private String direccion;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol = "CLIENTE";

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
