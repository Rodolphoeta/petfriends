package com.petfriends.pedidos.config;

import com.petfriends.pedidos.event.EventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    private static final String EXCHANGE = "com.petfriends.pedidos.service.PedidoService$PedidoEvent";

    @Bean
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate) {
        return event -> {
            try {
                String json = "{\"pedidoId\":\"" + event + "\",\"tipo\":\"evento\"}";
                System.out.println("[PEDIDOS] Publicando evento no RabbitMQ: " + json);
                rabbitTemplate.convertAndSend(EXCHANGE, "", json);
            } catch (Exception e) {
                System.err.println("[PEDIDOS] Erro ao publicar evento: " + e.getMessage());
            }
        };
    }
}