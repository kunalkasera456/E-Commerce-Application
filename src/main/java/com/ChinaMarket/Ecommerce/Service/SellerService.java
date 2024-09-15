package com.ChinaMarket.Ecommerce.Service;

import com.ChinaMarket.Ecommerce.Convertor.SellerConvertor;
import com.ChinaMarket.Ecommerce.Model.Seller;
import com.ChinaMarket.Ecommerce.Repository.SellerRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.SellerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public String addSeller(SellerRequestDto sellerRequestDTO) {

        Seller seller = SellerConvertor.SellerRequestDtoToSeller(sellerRequestDTO);

        sellerRepository.save(seller);
        return "Congrats! Now you can sell on Chine Market !!!";
    }
}
