package com.tienda.catalogo.service;

import com.tienda.catalogo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda.catalogo.dto.LoginRequest;
import com.tienda.catalogo.dto.RegistroRequest;
import com.tienda.catalogo.dto.UsuarioResponse;
import com.tienda.catalogo.exception.CredencialesInvalidasException;
import com.tienda.catalogo.exception.ReglaNegocioException;
import com.tienda.catalogo.model.Usuario;
import com.tienda.catalogo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest req) {
        String email = normalizarEmail(req.email());

        if (usuarioRepository.existsByEmail(email)) {
            throw new ReglaNegocioException("Ya existe una cuenta con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(req.nombre().trim());
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(req.password())); // se guarda el HASH
        usuario.setTelefono(req.telefono());
        usuario.setDireccion(req.direccion());
        // rol = CLIENTE por defecto (paso 1.3)

        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(normalizarEmail(req.email()))
                .filter(Usuario::isActivo)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.matches(req.password(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        return UsuarioResponse.desde(usuario);
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}
