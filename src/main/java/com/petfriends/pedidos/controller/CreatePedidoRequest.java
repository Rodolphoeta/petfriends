package com.petfriends.pedidos.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreatePedidoRequest {

    private UUID clienteId;
    private List<ItemDto> items;

    public static class ItemDto {
        private UUID produtoId;
        private String nome;
        private Integer quantidade;
        private BigDecimal precoUnitario;

        public UUID getProdutoId() { return produtoId; }
        public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    }

    public UUID getClienteId() { return clienteId; }
    public void setClienteId(UUID clienteId) { this.clienteId = clienteId; }
    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
}
