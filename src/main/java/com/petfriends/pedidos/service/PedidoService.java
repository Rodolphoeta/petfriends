package com.petfriends.pedidos.service;

import com.petfriends.pedidos.domain.OrderItem;
import com.petfriends.pedidos.domain.Pedido;
import com.petfriends.pedidos.domain.PedidoStatus;
import com.petfriends.pedidos.event.EventPublisher;
import com.petfriends.pedidos.repository.PedidoRepository;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EventPublisher eventPublisher;

    public PedidoService(PedidoRepository pedidoRepository, EventPublisher eventPublisher) {
        this.pedidoRepository = pedidoRepository;
        this.eventPublisher = eventPublisher;
    }

    public UUID criarPedido(UUID clienteId, List<OrderItem> items) {
        BigDecimal total = calcularTotal(items);
        Pedido pedido = new Pedido(clienteId, items, total);
        pedido.setStatus(PedidoStatus.NOVO);
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoCriado"));
        return pedido.getId();
    }

    public void confirmarPagamento(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.NOVO) {
            throw new IllegalStateException("Pagamento só pode ser confirmado em pedidos NOVO");
        }
        pedido.setStatus(PedidoStatus.FECHADO);
        pedido.setPaymentConfirmedAt(Instant.now());
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PagamentoConfirmado"));
    }

    public void marcarEmPreparacao(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.FECHADO) {
            throw new IllegalStateException("Só é possível marcar em preparação a partir de FECHADO");
        }
        pedido.setStatus(PedidoStatus.EM_PREPARACAO);
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoEmPreparacao"));
    }

    public void enviarPedido(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.FECHADO && pedido.getStatus() != PedidoStatus.EM_PREPARACAO) {
            throw new IllegalStateException("Enviar pedido só permitido a partir de FECHADO ou EM_PREPARACAO");
        }
        pedido.setStatus(PedidoStatus.ENVIADO);
        pedido.setShippedAt(Instant.now());
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoEnviado"));
    }

    public void cancelarPedido(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.FECHADO && pedido.getStatus() != PedidoStatus.EM_PREPARACAO) {
            throw new IllegalStateException("Cancelamento só permitido em FECHADO ou EM_PREPARACAO");
        }
        pedido.setStatus(PedidoStatus.CANCELADO);
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoCancelado"));
    }

    public void marcarEmTransito(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.ENVIADO) {
            throw new IllegalStateException("Só é possível marcar em trânsito a partir de ENVIADO");
        }
        pedido.setStatus(PedidoStatus.EM_TRANSITO);
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoEmTransito"));
    }

    public void marcarEntregue(UUID pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        if (pedido.getStatus() != PedidoStatus.EM_TRANSITO) {
            throw new IllegalStateException("Só é possível marcar entregue a partir de EM_TRANSITO");
        }
        pedido.setStatus(PedidoStatus.ENTREGUE);
        pedido.setDeliveredAt(Instant.now());
        pedidoRepository.save(pedido);
        eventPublisher.publish(new PedidoEvent(pedido.getId(), "PedidoEntregue"));
    }

    public Optional<com.petfriends.pedidos.domain.Pedido> buscarPedidoParaConsulta(UUID pedidoId) {
        return pedidoRepository.findById(pedidoId);
    }

    private Pedido buscarOuFalhar(UUID pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));
    }

    private BigDecimal calcularTotal(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static class PedidoEvent {
        private final UUID pedidoId;
        private final String tipo;

        public PedidoEvent(UUID pedidoId, String tipo) {
            this.pedidoId = pedidoId;
            this.tipo = tipo;
        }

        public UUID getPedidoId() {
            return pedidoId;
        }

        public String getTipo() {
            return tipo;
        }
    }
}
