//
////  CRIEI APENAS PARA TESTAR E VALIDAR A APLICAÇÃO no h2,
////  DEIXAREI @Component COMENTADO APÓS OS TESTES
//
//package com.petfriends.pedidos.test;
//
//
//import com.petfriends.pedidos.domain.OrderItem;
//import com.petfriends.pedidos.service.PedidoService;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PedidoTestRunner implements CommandLineRunner {
//
//    private final PedidoService pedidoService;
//
//    public PedidoTestRunner(PedidoService pedidoService) {
//        this.pedidoService = pedidoService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        UUID clienteId = UUID.randomUUID();
//        List<OrderItem> items = List.of(
//                new OrderItem(UUID.randomUUID(), "Ração Premium", 2, new BigDecimal("120.00"))
//        );
//        UUID pedidoId = pedidoService.criarPedido(clienteId, items);
//        System.out.println("Pedido criado: " + pedidoId);
//        pedidoService.confirmarPagamento(pedidoId);
//        System.out.println("Pagamento confirmado para: " + pedidoId);
//    }
//}
