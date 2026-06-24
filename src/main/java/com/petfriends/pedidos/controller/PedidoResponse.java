package com.petfriends.pedidos.controller;

import com.petfriends.pedidos.domain.OrderItem;
import com.petfriends.pedidos.domain.PedidoStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PedidoResponse {
    private UUID id;
    private UUID clienteId;
    private PedidoStatus status;
    private BigDecimal total;
    private Instant createdAt;
    private Instant paymentConfirmedAt;
    private Instant shippedAt;
    private Instant deliveredAt;
    private List<Item> items;

    public static class Item {
        private UUID produtoId;
        private String nome;
        private Integer quantidade;
        private BigDecimal precoUnitario;

        public Item() {}
        public Item(OrderItem oi) {
            this.produtoId = oi.getProdutoId();
            this.nome = oi.getNome();
            this.quantidade = oi.getQuantidade();
            this.precoUnitario = oi.getPrecoUnitario();
        }
        public UUID getProdutoId() { return produtoId; }
        public String getNome() { return nome; }
        public Integer getQuantidade() { return quantidade; }
        public BigDecimal getPrecoUnitario() { return precoUnitario; }
    }

    public PedidoResponse() {}

    public PedidoResponse(com.petfriends.pedidos.domain.Pedido p) {
        this.id = p.getId();
        this.clienteId = p.getClienteId();
        this.status = p.getStatus();
        this.total = p.getTotal();
        this.createdAt = p.getCreatedAt();
        this.paymentConfirmedAt = p.getPaymentConfirmedAt();
        this.shippedAt = p.getShippedAt();
        this.deliveredAt = p.getDeliveredAt();
        this.items = p.getItems().stream().map(Item::new).collect(Collectors.toList());
    }

    public UUID getId() { return id; }
    public UUID getClienteId() { return clienteId; }
    public PedidoStatus getStatus() { return status; }
    public BigDecimal getTotal() { return total; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getPaymentConfirmedAt() { return paymentConfirmedAt; }
    public Instant getShippedAt() { return shippedAt; }
    public Instant getDeliveredAt() { return deliveredAt; }
    public List<Item> getItems() { return items; }
}
