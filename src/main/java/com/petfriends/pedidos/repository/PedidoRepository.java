package com.petfriends.pedidos.repository;

import com.petfriends.pedidos.domain.Pedido;
import com.petfriends.pedidos.domain.PedidoStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByStatusAndShippedAtBefore(PedidoStatus status, Instant before);
}
