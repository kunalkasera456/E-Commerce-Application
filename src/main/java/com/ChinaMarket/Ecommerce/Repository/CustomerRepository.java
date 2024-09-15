package com.ChinaMarket.Ecommerce.Repository;

import com.ChinaMarket.Ecommerce.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
}
