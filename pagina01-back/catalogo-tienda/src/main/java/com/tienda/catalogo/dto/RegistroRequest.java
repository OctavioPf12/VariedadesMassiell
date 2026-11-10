package com.tienda.catalogo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "100 caracteres maximo")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Formato de email invalido")
        @Size (max = 120)
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
        String password,

        @Size(max = 20)
        String telefono,

        @Size(max = 255)
        String direccion

) {
}
