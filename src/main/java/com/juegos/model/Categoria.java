package com.juegos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "categoria")
@Data 
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}