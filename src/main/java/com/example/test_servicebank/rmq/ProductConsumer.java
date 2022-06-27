package com.example.test_servicebank.rmq;

import com.example.test_servicebank.entity.Product;
import com.example.test_servicebank.repository.CustomerRepository;
import com.example.test_servicebank.repository.ProductRepository;
import com.example.test_servicebank.repository.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductConsumer {

    private ProductRepository productRepository;

    public ProductConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = "${product.amqp.queue}")
    public void receiveProduct(Product product) {
        log.info("Received product: "+product);
        productRepository.save(product);
    }

}

