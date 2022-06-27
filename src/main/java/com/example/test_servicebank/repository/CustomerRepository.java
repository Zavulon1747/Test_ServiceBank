package com.example.test_servicebank.repository;

import com.example.test_servicebank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select * from customer where first_name=?1 and salary=?2 and claim=?3 and debt=?4", nativeQuery = true)
    public Customer getCustomerByFirstNameAndSalaryAndClaimAndDebt(String firstName, int salary, int claim, boolean debt);


}
