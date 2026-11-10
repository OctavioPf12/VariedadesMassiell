package com.tienda.catalogo.repository;

import com.tienda.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Producto, Long>{
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoria_IdAndActivoTrue(Long categoriaId);
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
}
