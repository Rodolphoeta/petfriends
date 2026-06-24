package com.petfriends.almoxarifado.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produtos_estoque")
public class ProdutoEstoque implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String produtoId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int quantidadeDisponivel;

    @Embedded
    private Dimensoes dimensoes;

    public ProdutoEstoque() {}

    public ProdutoEstoque(String produtoId, String nome, int quantidadeDisponivel, Dimensoes dimensoes) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dimensoes = dimensoes;
    }

    public void debitarEstoque(int quantidade) {
        if (quantidade > this.quantidadeDisponivel) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + this.nome);
        }
        this.quantidadeDisponivel -= quantidade;
    }

    public void reporEstoque(int quantidade) {
        this.quantidadeDisponivel += quantidade;
    }

    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
    public Dimensoes getDimensoes() { return dimensoes; }
    public void setDimensoes(Dimensoes dimensoes) { this.dimensoes = dimensoes; }
}
