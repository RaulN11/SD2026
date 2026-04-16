package com.rauln.CarKet.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String AD_CREATED_QUEUE = "ad.created.queue";
    public static final String AD_DELETED_QUEUE = "ad.deleted.queue";
    public static final String AD_EXCHANGE = "ad.exchange";
    public static final String AD_CREATED_KEY = "ad.created.key";
    public static final String AD_DELETED_KEY = "ad.deleted.key";

    @Bean
    public Queue adCreatedQueue() {
        return new Queue(AD_CREATED_QUEUE);
    }

    @Bean
    public Queue adDeletedQueue() {
        return new Queue(AD_DELETED_QUEUE);
    }

    @Bean
    public DirectExchange adExchange() {
        return new DirectExchange(AD_EXCHANGE);
    }

    @Bean
    public Binding createdBinding(Queue adCreatedQueue, DirectExchange adExchange) {
        return BindingBuilder.bind(adCreatedQueue).to(adExchange).with(AD_CREATED_KEY);
    }

    @Bean
    public Binding deletedBinding(Queue adDeletedQueue, DirectExchange adExchange) {
        return BindingBuilder.bind(adDeletedQueue).to(adExchange).with(AD_DELETED_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}