package com.juegos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroClienteDto {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, max = 80, message = "Los nombres deben tener entre 2 y 80 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 80, message = "Los apellidos deben tener entre 2 y 80 caracteres")
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingrese un correo electrónico válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 30, message = "La contraseña debe tener entre 4 y 30 caracteres")
    private String password;

    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmarPassword;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe contener 9 dígitos numéricos")
    private String telefono;

    @Size(max = 150, message = "La dirección no puede exceder 150 caracteres")
    private String direccion;
}
