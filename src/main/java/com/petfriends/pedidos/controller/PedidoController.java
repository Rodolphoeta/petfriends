package com.petfriends.pedidos.controller;

import com.petfriends.pedidos.domain.OrderItem;
import com.petfriends.pedidos.service.PedidoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<CreatePedidoResponse> criarPedido(@RequestBody CreatePedidoRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(i -> new OrderItem(i.getProdutoId(), i.getNome(), i.getQuantidade(), i.getPrecoUnitario()))
                .collect(Collectors.toList());
        UUID id = pedidoService.criarPedido(request.getClienteId(), items);
        return ResponseEntity.ok(new CreatePedidoResponse(id));
    }


    @PostMapping("/{id}/confirmar-pagamento")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable("id") UUID id) {
        pedidoService.confirmarPagamento(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviarPedido(@PathVariable("id") UUID id) {
        pedidoService.enviarPedido(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable("id") UUID id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable("id") UUID id) {
        return pedidoService.buscarPedidoParaConsulta(id)
                .map(PedidoResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
