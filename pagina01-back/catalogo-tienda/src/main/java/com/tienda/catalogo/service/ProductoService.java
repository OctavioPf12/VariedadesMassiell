package com.tienda.catalogo.service;

import com.tienda.catalogo.dto.ProductResponse;
import com.tienda.catalogo.dto.ProductoRequest;
import com.tienda.catalogo.exception.RecursoNoEncontradoException;
import com.tienda.catalogo.model.Producto;
import com.tienda.catalogo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {
    private final ProductRepository productRepository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductRepository productRepository, CategoriaService categoriaService){
        this.productRepository = productRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listar(Long categoriaId, String busqueda){
        List<Producto> productos;

        if (categoriaId != null) {
            productos = productRepository.findByCategoria_IdAndActivoTrue(categoriaId);
        } else if (busqueda != null && !busqueda.isBlank()) {
            productos = productRepository
                    .findByNombreContainingIgnoreCaseAndActivoTrue(busqueda.trim());
        } else {
            productos = productRepository.findByActivoTrue();
        }

        return productos.stream().map(ProductResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse obtener(Long id){
        return ProductResponse.desde(buscarActivo(id));


    }
    @Transactional
    public ProductResponse crear(ProductoRequest req){
        Producto producto = new Producto();
        aplicarDatos(producto, req);
        return ProductResponse.desde(productRepository.save(producto));

    }

    @Transactional
    public ProductResponse actualizar(Long id, ProductoRequest req) {
        Producto producto = buscarActivo(id);
        aplicarDatos(producto, req);
        return ProductResponse.desde(producto);
    }

    @Transactional
    public void desactivar(Long id) {
        buscarActivo(id).setActivo(false);
    }

    private void aplicarDatos(Producto producto, ProductoRequest req) {
        producto.setNombre(req.nombre());
        producto.setDescripcion(req.descripcion());
        producto.setPrecio(req.precio());
        producto.setImagenUrl(req.imagenUrl());
        producto.setCategoria(categoriaService.buscar(req.categoriaId()));
    }

    public Producto buscarActivo(Long id) {
        return productRepository.findById(id)
                .filter(Producto::isActivo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el producto con id " + id));
    }



}
