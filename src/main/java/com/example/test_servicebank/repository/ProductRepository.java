package com.example.test_servicebank.repository;

import com.example.test_servicebank.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Integer> {

    @Query(value = "select * from product where id in (select product_id from rule where salary <= (select salary from customer where id=?1)" +
            "and if((select debt from customer where id =?1), debt = true, debt in(true, false)))", nativeQuery = true)
    List<Product> findAllProductsByCustomerId(int customerId);



}
