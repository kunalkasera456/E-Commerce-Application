package com.ChinaMarket.Ecommerce.Repository;

import com.ChinaMarket.Ecommerce.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
}
