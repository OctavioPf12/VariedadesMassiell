package com.tienda.catalogo.repository;

import com.tienda.catalogo.model.EstadoPedido;
import com.tienda.catalogo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByUsuarioIdOrderByFechaPedidoDesc(Long usuarioId);

    List<Pedido> findByEstadoOrderByFechaPedidoAsc(EstadoPedido estado);
}
