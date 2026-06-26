package com.petfriends.pedidos.controller;

import com.petfriends.pedidos.domain.OrderItem;
import com.petfriends.pedidos.service.PedidoService;
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
    public ResponseEntity<String> confirmarPagamento(@PathVariable("id") UUID id) {
        pedidoService.confirmarPagamento(id);
        return ResponseEntity.ok("Pagamento confirmado com sucesso. Status: FECHADO");
    }

    @PostMapping("/{id}/em-preparacao")
    public ResponseEntity<String> marcarEmPreparacao(@PathVariable("id") UUID id) {
        pedidoService.marcarEmPreparacao(id);
        return ResponseEntity.ok("Pedido em preparação. Status: EM_PREPARACAO");
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<String> enviarPedido(@PathVariable("id") UUID id) {
        pedidoService.enviarPedido(id);
        return ResponseEntity.ok("Pedido enviado. Status: ENVIADO");
    }

    @PostMapping("/{id}/em-transito")
    public ResponseEntity<String> marcarEmTransito(@PathVariable("id") UUID id) {
        pedidoService.marcarEmTransito(id);
        return ResponseEntity.ok("Pedido em trânsito. Status: EM_TRANSITO");
    }

    @PostMapping("/{id}/entregar")
    public ResponseEntity<String> marcarEntregue(@PathVariable("id") UUID id) {
        pedidoService.marcarEntregue(id);
        return ResponseEntity.ok("Pedido entregue com sucesso. Status: ENTREGUE");
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable("id") UUID id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok("Pedido cancelado. Status: CANCELADO");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable("id") UUID id) {
        return pedidoService.buscarPedidoParaConsulta(id)
                .map(PedidoResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
