package com.petfriends.transporte.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTransporteConfig {

    public static final String QUEUE_TRANSPORTE = "petfriends.pedidos.v1.pedido-criado.transporte";
    public static final String EXCHANGE_PEDIDOS = "com.petfriends.pedidos.service.PedidoService$PedidoEvent";

    @Bean
    public Queue transporteQueue() {
        return new Queue(QUEUE_TRANSPORTE, true);
    }

    @Bean
    public FanoutExchange pedidosExchangeTransporte() {
        return new FanoutExchange(EXCHANGE_PEDIDOS, true, false);
    }

    @Bean
    public Binding bindingTransporte(Queue transporteQueue, FanoutExchange pedidosExchangeTransporte) {
        return BindingBuilder.bind(transporteQueue).to(pedidosExchangeTransporte);
    }

    @Bean
    public MessageConverter messageConverterTransporte() {
        return new JacksonJsonMessageConverter();
    }
}
