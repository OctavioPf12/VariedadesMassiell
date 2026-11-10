package com.tienda.catalogo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombre,

        @Size(max = 1000)
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value =  "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @Size(max = 255)
        String imagenUrl,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId


) {
}
