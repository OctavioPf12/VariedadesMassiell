package com.tienda.catalogo.dto;

import com.tienda.catalogo.model.Producto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String imagenUrl, Long categoriaId,
        String categoriaNombre
) {
    public static ProductResponse desde(Producto p) {
        return new ProductResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getImagenUrl(),
                p.getCategoria().getId(),
                p.getCategoria().getNombre()
        );
    }
}
