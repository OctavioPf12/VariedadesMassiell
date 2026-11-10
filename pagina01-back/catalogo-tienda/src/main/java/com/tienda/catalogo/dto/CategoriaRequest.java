package com.tienda.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CategoriaRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "50 caracteres maximo")
    String nombre,

    @Size(max = 255)
    String descripcion



){}
