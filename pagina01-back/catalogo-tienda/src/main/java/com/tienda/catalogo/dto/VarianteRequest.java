package com.tienda.catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VarianteRequest(
        @NotBlank(message = "La talla es obligatoria")
        @Size(max = 10, message = "30 caracteres maximo")
        String talla,

        @NotBlank(message = "El color es obligatorio")
        @Size(max = 30, message ="30 caracteres maximo")
        String color,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {
}
