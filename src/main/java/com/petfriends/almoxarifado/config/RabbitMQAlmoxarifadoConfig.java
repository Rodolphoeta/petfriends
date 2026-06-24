package com.petfriends.almoxarifado.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQAlmoxarifadoConfig {

    public static final String QUEUE_ALMOXARIFADO = "petfriends.pedidos.v1.pedido-criado.almoxarifado";
    public static final String EXCHANGE_PEDIDOS = "com.petfriends.pedidos.service.PedidoService$PedidoEvent";

    @Bean
    public Queue almoxarifadoQueue() {
        return new Queue(QUEUE_ALMOXARIFADO, true);
    }

    @Bean
    public FanoutExchange pedidosExchange() {
        return new FanoutExchange(EXCHANGE_PEDIDOS, true, false);
    }

    @Bean
    public Binding bindingAlmoxarifado(Queue almoxarifadoQueue, FanoutExchange pedidosExchange) {
        return BindingBuilder.bind(almoxarifadoQueue).to(pedidosExchange);
    }

    @Bean
    public MessageConverter messageConverterAlmoxarifado() {
        return new JacksonJsonMessageConverter();
    }
}
