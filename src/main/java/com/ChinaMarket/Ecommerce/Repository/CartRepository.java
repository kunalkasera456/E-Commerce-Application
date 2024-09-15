package com.ChinaMarket.Ecommerce.Repository;

import com.ChinaMarket.Ecommerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


}
