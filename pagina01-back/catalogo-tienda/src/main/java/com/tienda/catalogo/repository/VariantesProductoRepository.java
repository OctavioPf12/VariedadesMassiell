package com.tienda.catalogo.repository;


import com.tienda.catalogo.model.VarianteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VariantesProductoRepository extends JpaRepository<VarianteProducto, Long>{
    List<VarianteProducto> findByProducto_Id(Long productoId);

    List<VarianteProducto> findByProducto_IdAndStockGreaterThan(Long productoId, Integer stock);

    boolean existsByProducto_IdAndTallaAndColor(Long productoId, String talla, String color);
}
