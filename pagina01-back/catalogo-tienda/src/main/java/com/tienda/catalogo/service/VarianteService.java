package com.tienda.catalogo.service;

import com.tienda.catalogo.dto.VarianteRequest;
import com.tienda.catalogo.dto.VarianteResponse;
import com.tienda.catalogo.exception.RecursoNoEncontradoException;
import com.tienda.catalogo.exception.ReglaNegocioException;
import com.tienda.catalogo.model.Producto;
import com.tienda.catalogo.model.VarianteProducto;
import com.tienda.catalogo.repository.VariantesProductoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VarianteService {
    private final VariantesProductoRepository variantesProductoRepository;
    private final ProductoService productoService;

    public VarianteService(VariantesProductoRepository variantesProductoRepository,ProductoService productoService){
        this.variantesProductoRepository = variantesProductoRepository;
        this.productoService = productoService;
    }

    @Transactional(readOnly = true)
    public List<VarianteResponse> listar(Long productoId, boolean soloDisponibles){
        productoService.buscarActivo(productoId);

        List<VarianteProducto> variantes = soloDisponibles
                ? variantesProductoRepository.findByProducto_IdAndStockGreaterThan(productoId, 0)
                : variantesProductoRepository.findByProducto_Id(productoId);

        return variantes.stream().map(VarianteResponse::desde).toList();

    }

    @Transactional
    public VarianteResponse crear(Long productoId, VarianteRequest req){
        Producto producto = productoService.buscarActivo(productoId);
        String talla = normalizarTalla(req.talla());
        String color = normalizarColor(req.color());

        if (variantesProductoRepository.existsByProducto_IdAndTallaAndColor(productoId, talla, color)){
            throw new ReglaNegocioException("Ya existe la variante" + talla + "/" + color + "para este producto");
        }

        VarianteProducto varianteProducto = new VarianteProducto();
        varianteProducto.setProducto(producto);
        varianteProducto.setTalla(talla);
        varianteProducto.setColor(color);
        varianteProducto.setStock(req.stock());

        return VarianteResponse.desde(variantesProductoRepository.save(varianteProducto));
    }

    @Transactional
    public VarianteResponse actualizar(Long productoId, Long varianteId, VarianteRequest req){
        VarianteProducto varianteProducto = buscar(productoId, varianteId);
        String talla = normalizarTalla(req.talla());
        String color = normalizarColor(req.color());

        boolean cambioCombinacion = !varianteProducto.getTalla().equals(talla) || !varianteProducto.getColor().equals(color);

        if (cambioCombinacion && variantesProductoRepository.existsByProducto_IdAndTallaAndColor(productoId,talla,color)){
            throw new ReglaNegocioException("Ya existe la variante" + talla + "/" + color + "para este producto");
        }

        varianteProducto.setTalla(talla);
        varianteProducto.setColor(color);
        varianteProducto.setStock(req.stock());
        return VarianteResponse.desde(varianteProducto);

    }

    public VarianteProducto buscar(Long productoId, Long varianteId){
        return variantesProductoRepository.findById(varianteId).filter(v -> v.getProducto().getId().equals(productoId)).orElseThrow(() -> new RecursoNoEncontradoException("No exsiste la variante" + varianteId + "en el producto" + productoId));

    }

    private String normalizarTalla(String talla){
        return talla.trim().toUpperCase();
    }

    private String normalizarColor(String color){
        String c = color.trim().toLowerCase();
        return  Character.toUpperCase(c.charAt(0)) + c.substring(1);
    }


}
