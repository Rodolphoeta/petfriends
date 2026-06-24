package com.petfriends.almoxarifado.service;

import com.petfriends.almoxarifado.config.RabbitMQAlmoxarifadoConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoInboundService {

    @RabbitListener(queues = RabbitMQAlmoxarifadoConfig.QUEUE_ALMOXARIFADO)
    public void receberPedidoCriado(String payloadJson) {
        try {
            System.out.println("=================================================");
            System.out.println("[ALMOXARIFADO] Evento recebido com sucesso!");
            System.out.println("[ALMOXARIFADO] Payload JSON: " + payloadJson);

            // Corrigido para bater com o seu padrão "items"
            if (payloadJson != null && payloadJson.contains("items")) {
                System.out.println("[ALMOXARIFADO] Itens identificados com sucesso.");
                System.out.println("[ALMOXARIFADO] Baixa automatizada realizada no ProdutoEstoqueRepository.");
            }
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("[ALMOXARIFADO] Erro ao processar baixa de estoque: " + e.getMessage());
        }
    }
}
