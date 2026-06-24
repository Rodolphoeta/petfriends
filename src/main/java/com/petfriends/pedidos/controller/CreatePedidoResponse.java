package com.petfriends.pedidos.controller;

import java.util.UUID;

public class CreatePedidoResponse {
    private UUID id;

    public CreatePedidoResponse() {}

    public CreatePedidoResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
