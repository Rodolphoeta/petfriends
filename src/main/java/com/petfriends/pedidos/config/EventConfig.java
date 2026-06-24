package com.petfriends.pedidos.config;

import com.petfriends.pedidos.event.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public EventPublisher eventPublisher() {
        return event -> System.out.println("Published event: " + event);
    }
}
