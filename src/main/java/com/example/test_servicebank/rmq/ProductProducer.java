package com.example.test_servicebank.rmq;

import com.example.test_servicebank.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductProducer {

    //create a queue for product    and declare it in rabbitmq

    private RabbitTemplate rabbitTemplate;

    public ProductProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTo(@Value("${product.amqp.queue}") String queueAddress, Product product) {
        log.info("Sending product to queue: "+queueAddress);
        rabbitTemplate.convertAndSend(queueAddress, product);
    }



}
