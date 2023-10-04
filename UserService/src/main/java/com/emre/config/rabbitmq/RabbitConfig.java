package com.emre.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //Kuyruk sistemimizde kullanmak isteidğimiz parametreleri tanımlıyoruz.

    private String exchangeName="auth-exchange";
    private String queueName="auth-queue-create-user";
    private String routingOrBindingKey="auth-routing-key-create-user";

    //kuyruk ile binding bağlanması gereklidir. bunun için dbinding nesnesi olmalıdır.
    //hangi yapıyı kullancaksın genelde directexchange kullanılır. @Bean tagi ile
    @Bean
    DirectExchange directAuthExchange(){
        return new DirectExchange(exchangeName);
    }
    //mesajların iletileceği dinleyenlerin okuyabileceği kuyrugu tanımlıyoruz.
    @Bean
    Queue authQueue(){
        return new Queue(queueName);
    }

    // kuyruk ile excchange arasdna olan bağlantıyıı binding nesnesi ile kurariz
    @Bean
    public Binding bindingAuthCreateUser
            (final Queue authQueue,final DirectExchange directAuthExchange){
        return BindingBuilder.bind(authQueue).to(directAuthExchange)
                .with(routingOrBindingKey);
    }












}
