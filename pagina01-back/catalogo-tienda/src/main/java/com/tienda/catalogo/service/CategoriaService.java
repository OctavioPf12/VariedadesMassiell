package com.tienda.catalogo.service;

import com.tienda.catalogo.dto.CategoriaRequest;
import com.tienda.catalogo.dto.CategoriaResponse;
import com.tienda.catalogo.exception.RecursoNoEncontradoException;
import com.tienda.catalogo.exception.ReglaNegocioException;
import com.tienda.catalogo.model.Categoria;
import com.tienda.catalogo.repository.CategoriaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponse::desde)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse obtener(Long id) {
        return CategoriaResponse.desde(buscar(id));
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest req) {
        if (categoriaRepository.existsByNombre(req.nombre())) {
            throw new ReglaNegocioException("Ya existe la categoría '" + req.nombre() + "'");
        }
        Categoria categoria = new Categoria(req.nombre(), req.descripcion());
        return CategoriaResponse.desde(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse actualizar(Long id, CategoriaRequest req) {
        Categoria categoria = buscar(id);
        if (!categoria.getNombre().equals(req.nombre())
                && categoriaRepository.existsByNombre(req.nombre())) {
            throw new ReglaNegocioException("Ya existe la categoría '" + req.nombre() + "'");
        }
        categoria.setNombre(req.nombre());
        categoria.setDescripcion(req.descripcion());
        return CategoriaResponse.desde(categoria);
    }

    public Categoria buscar(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la categoría con id " + id));
    }
}
