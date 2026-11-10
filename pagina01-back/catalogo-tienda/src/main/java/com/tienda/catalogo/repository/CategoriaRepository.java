package com.tienda.catalogo.repository;

import com.tienda.catalogo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    boolean existsByNombre(String nombre);
}
