package com.github.yasminmuniz25.ms_pedidos.repositories;

import com.github.yasminmuniz25.ms_pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
