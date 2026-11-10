package com.tienda.catalogo.controller;

import com.tienda.catalogo.dto.CategoriaRequest;
import com.tienda.catalogo.dto.CategoriaResponse;
import com.tienda.catalogo.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponse> listar(){
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponse obtener(@PathVariable Long id){
        return categoriaService.obtener(id);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(req));
    }

    @PutMapping("/{id}")
    public CategoriaResponse actualizar(@PathVariable Long id,@Valid @RequestBody CategoriaRequest req){
    return categoriaService.actualizar(id, req);
    }
}
