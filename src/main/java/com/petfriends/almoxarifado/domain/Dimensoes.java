package com.petfriends.almoxarifado.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Dimensoes {

    private double altura;
    private double largura;
    private double profundidade;
    private double peso;

    protected Dimensoes() {}

    public Dimensoes(double altura, double largura, double profundidade, double peso) {
        if (altura <= 0 || largura <= 0 || profundidade <= 0 || peso <= 0) {
            throw new IllegalArgumentException("As dimensões e o peso devem ser maiores que zero.");
        }
        this.altura = altura;
        this.largura = largura;
        this.profundidade = profundidade;
        this.peso = peso;
    }

    public double getAltura() { return altura; }
    public double getLargura() { return largura; }
    public double getProfundidade() { return profundidade; }
    public double getPeso() { return peso; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimensoes dimensoes = (Dimensoes) o;
        return Double.compare(dimensoes.altura, altura) == 0 &&
                Double.compare(dimensoes.largura, largura) == 0 &&
                Double.compare(dimensoes.profundidade, profundidade) == 0 &&
                Double.compare(dimensoes.peso, peso) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altura, largura, profundidade, peso);
    }
}
