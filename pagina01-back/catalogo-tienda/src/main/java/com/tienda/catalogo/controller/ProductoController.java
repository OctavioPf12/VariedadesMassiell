package com.tienda.catalogo.controller;


import com.tienda.catalogo.dto.ProductResponse;
import com.tienda.catalogo.dto.ProductoRequest;
import com.tienda.catalogo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductResponse> listar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(name = "q", required = false) String busqueda) {
        return productoService.listar(categoriaId, busqueda);
    }

    @GetMapping("/{id}")
    public ProductResponse obtener(@PathVariable Long id) {
        return productoService.obtener(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> crear(@Valid @RequestBody ProductoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(req));
    }

    @PutMapping("/{id}")
    public ProductResponse actualizar(@PathVariable Long id,
                                       @Valid @RequestBody ProductoRequest req) {
        return productoService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
