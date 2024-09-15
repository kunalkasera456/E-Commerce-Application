package com.ChinaMarket.Ecommerce.Convertor;

import com.ChinaMarket.Ecommerce.Model.Seller;
import com.ChinaMarket.Ecommerce.RequestDTO.SellerRequestDto;

public class SellerConvertor {

    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDTO) {

        return Seller.builder()
                .name(sellerRequestDTO.getName())
                .mobNo(sellerRequestDTO.getMobNo())
                .email(sellerRequestDTO.getEmail())
                .panNo(sellerRequestDTO.getPanNo())
                .build();
    }
}
