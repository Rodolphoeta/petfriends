package com.petfriends.transporte.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "entregas")
public class Entrega implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String pedidoId;

    @Column(nullable = false)
    private String statusEntrega;

    @Embedded
    private Endereco enderecoDestino;

    public Entrega() {}

    public Entrega(String pedidoId, Endereco enderecoDestino) {
        this.pedidoId = pedidoId;
        this.statusEntrega = "PENDENTE";
        this.enderecoDestino = enderecoDestino;
    }

    public void atualizarStatus(String novoStatus) {
        this.statusEntrega = novoStatus;
    }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }
    public String getStatusEntrega() { return statusEntrega; }
    public void setStatusEntrega(String statusEntrega) { this.statusEntrega = statusEntrega; }
    public Endereco getEnderecoDestino() { return enderecoDestino; }
    public void setEnderecoDestino(Endereco enderecoDestino) { this.enderecoDestino = enderecoDestino; }
}
