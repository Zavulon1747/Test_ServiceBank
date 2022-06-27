package com.example.test_servicebank.repository;

import com.example.test_servicebank.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    @Query(value = "select * from rule where salary <= (select salary from customer where id=?1) " +
            "and if((select debt from customer where id=?1), debt = true, debt in (true, false))" +
            "order by debt desc", nativeQuery = true)
    public List<Rule> findRulesByCustomerId(int customerId);

    @Query(value = "select * from rule where product_id=?1 and id=?2", nativeQuery = true)
    public Rule deleteRuleByProductId(int productId, int ruleId);

    @Query(value = "select * from rule where removed = false",nativeQuery = true)
    public List<Rule> findAllNotRemovedRules();

    @Query(value = "select * from rule where removed = true",nativeQuery = true)
    public List<Rule> findAllRemovedRules();
}
