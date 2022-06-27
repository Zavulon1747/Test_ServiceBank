package com.example.test_servicebank.Config;

import com.example.test_servicebank.entity.Product;
import com.example.test_servicebank.rmq.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductSender {

    //create productSender to send product to queue and declare it in rabbitmq
    @Autowired
    private ProductProducer productProducer;

    @Value("${product.amqp.queue}")
    private String queueAddress;

    public void sendTo(Product product) {
        productProducer.sendTo(queueAddress, product);
    }



}
