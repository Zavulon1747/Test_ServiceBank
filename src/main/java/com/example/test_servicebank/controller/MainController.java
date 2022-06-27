package com.example.test_servicebank.controller;

import com.example.test_servicebank.Config.ProductSender;
import com.example.test_servicebank.entity.Customer;
import com.example.test_servicebank.entity.Product;
import com.example.test_servicebank.entity.Rule;
import com.example.test_servicebank.repository.CustomerRepository;
import com.example.test_servicebank.repository.ProductRepository;
import com.example.test_servicebank.repository.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class MainController {

    ProductRepository productRepository;
    CustomerRepository customerRepository;
    RuleRepository ruleRepository;

    @Value("${product.amqp.queue}")
    private String queueAddress;
    private ProductSender productSender;

    @Autowired
    public MainController(RuleRepository ruleRepository, ProductRepository productRepository, CustomerRepository customerRepository, ProductSender productSender) {
        this.ruleRepository = ruleRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.productSender = productSender;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    } //got it

    @GetMapping("/{customerId}/rules")
    public List<Rule> getAllRulesByCustomerId(@PathVariable int customerId) {
        log.info("All rules had been gotten!");
        return ruleRepository.findRulesByCustomerId(customerId);
    } //got it

    @GetMapping("/{customerId}/products_instead_rules")
    public List<Product> getAllProductsByCustomerId(@PathVariable int customerId) {
        log.info("All products had been gotten!");
        return productRepository.findAllProductsByCustomerId(customerId);
    } //got it -- instead of POST/products/apply

    @PostMapping("/{productId}/rules")
    public void addRuleToProduct(@PathVariable int productId, @RequestBody Rule rule) {
        rule.setProductId(productId);
        ruleRepository.save(rule);
    } //got it

    @DeleteMapping("{product_id}/rules/{rule_id}")
    public void deleteRuleByProductId(@PathVariable int product_id, @PathVariable int rule_id) {
        Rule rule = ruleRepository.deleteRuleByProductId(product_id, rule_id);
        rule.setRemoved(true);
        ruleRepository.save(rule);
        log.info("Rule was marked as \"removed=true\"");
    } //got it

    @GetMapping("/all_rules")
    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    } //got it

    @GetMapping("/rules")
    public List<Rule> getAllActiveRules() {
        return ruleRepository.findAllNotRemovedRules();
    } //got it

    @GetMapping("/rules_removed")
    public List<Rule> getAllRemovedRules() {
        return ruleRepository.findAllRemovedRules();
    } //got it

    @PostMapping("/apply")
    public RedirectView getProductsToCustomer(@RequestBody Customer customer) {
        Customer customerAlreadyExists = customerRepository.getCustomerByFirstNameAndSalaryAndClaimAndDebt(
                customer.getFirstName(), customer.getSalary(), customer.getClaim(), customer.isDebt());
        if (customerAlreadyExists!=null) {
            log.info("Preventing duplicate!");
            log.info("Customer was saved in DataBase and now you will be redirected to GET-method!");
            return new RedirectView("/products/"+customerAlreadyExists.getId()+"/products_instead_rules");
        }
        customerRepository.save(customer);
        log.info("Customer was saved in DataBase and now you will be redirected to GET-method!");
        return new RedirectView("/products/"+customer.getId()+"/products_instead_rules");


    }


    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        log.info("Product was added!");
        productSender.sendTo(product);
        log.info("Product was sent");
    }

    //





}
