package com.tienda.catalogo.dto;

import com.tienda.catalogo.model.Rol;
import com.tienda.catalogo.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String direccion,
        Rol rol
) {
    public static UsuarioResponse desde(Usuario u){
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail(), u.getTelefono(), u.getDireccion(), u.getRol());
    }
}
