package com.petfriends.transporte.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Endereco {

    private String logradouro;
    private String numero;
    private String cidade;
    private String cep;

    protected Endereco() {}

    public Endereco(String logradouro, String numero, String cidade, String cep) {
        if (logradouro == null || numero == null || cidade == null || cep == null) {
            throw new IllegalArgumentException("Nenhum campo do endereço pode ser nulo.");
        }
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
    }

    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getCidade() { return cidade; }
    public String getCep() { return cep; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) &&
                Objects.equals(numero, endereco.numero) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cidade, cep);
    }
}
