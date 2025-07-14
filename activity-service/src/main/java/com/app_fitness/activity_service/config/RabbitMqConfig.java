package com.app_fitness.activity_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue activityQueue(){
        return new Queue(queueName, true);
    }

    //crea un exchange fitness.exchange all avvio dell app
    //il msg viene inviato solo se la routing key è uguale a quella del file .yml
    @Bean
    public DirectExchange activityExchange(){
        return new DirectExchange(exchange);
    }

    //Quando un msg viene inviato al queue con la routing.key giusta, rabbitmq lo mette in coda
    //alla queue asssegnata (activity queue)
    @Bean
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange){
        return BindingBuilder.bind(activityQueue).to(activityExchange).with(routingKey);
    }

    //Converte l oggetto java in un json da mandare al queue
    @Bean
    public MessageConverter messageConvertJson(){
        return new Jackson2JsonMessageConverter();
    }
}
