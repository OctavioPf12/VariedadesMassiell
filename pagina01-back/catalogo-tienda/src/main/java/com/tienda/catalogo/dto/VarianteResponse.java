package com.tienda.catalogo.dto;

import com.tienda.catalogo.model.VarianteProducto;

public record VarianteResponse(Long id, Long productoId, String talla, String color, Integer stock) {
    public static VarianteResponse desde(VarianteProducto v){
        return new VarianteResponse(
                v.getId(),
                v.getProducto().getId(),
                v.getTalla(),
                v.getColor(),
                v.getStock()

        );
    }
}
