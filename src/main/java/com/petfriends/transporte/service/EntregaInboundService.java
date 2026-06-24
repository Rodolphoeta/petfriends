package com.petfriends.transporte.service;

import com.petfriends.transporte.config.RabbitMQTransporteConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EntregaInboundService {

    @RabbitListener(queues = RabbitMQTransporteConfig.QUEUE_TRANSPORTE)
    public void receberPedidoParaEntrega(String payloadJson) {
        try {
            System.out.println("=================================================");
            System.out.println("[TRANSPORTE] Evento recebido com sucesso!");
            System.out.println("[TRANSPORTE] Payload JSON: " + payloadJson);
            System.out.println("[TRANSPORTE] Gerando nova guia de envio logístico...");
            System.out.println("=================================================");
        } catch (Exception e) {
            System.err.println("[TRANSPORTE] Erro ao processar entrega: " + e.getMessage());
        }
    }
}
