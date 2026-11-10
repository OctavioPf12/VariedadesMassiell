package com.tienda.catalogo.controller;

import com.tienda.catalogo.dto.VarianteRequest;
import com.tienda.catalogo.dto.VarianteResponse;
import com.tienda.catalogo.service.VarianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos/{productoId}/variantes")
public class VarianteController {
    private final VarianteService varianteService;

    public VarianteController(VarianteService varianteService){
        this.varianteService = varianteService;
    }

    @GetMapping
    public List<VarianteResponse> listar(@PathVariable Long productoId, @RequestParam(defaultValue = "false") boolean disponibles){
        return varianteService.listar(productoId, disponibles);
    }

    @PostMapping
    public ResponseEntity<VarianteResponse> crear(@PathVariable Long productoId, @Valid @RequestBody VarianteRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(varianteService.crear(productoId, req));
    }

    @PutMapping("/{varianteId}")
    public VarianteResponse actualizar(@PathVariable Long productoId, @PathVariable Long varianteId, @Valid @RequestBody VarianteRequest req) {
        return varianteService.actualizar(productoId, varianteId, req);
    }
}
