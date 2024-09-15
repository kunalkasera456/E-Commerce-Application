package com.ChinaMarket.Ecommerce.Convertor;

import com.ChinaMarket.Ecommerce.Enum.ProductStatus;
import com.ChinaMarket.Ecommerce.Model.Product;
import com.ChinaMarket.Ecommerce.RequestDTO.ProductRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.ProductResponseDto;

public class ProductConvertor {

    public static Product productRequestDtotoProduct(ProductRequestDto productRequestDto) {

        return Product.builder()
                .productName(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .productCategory(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto productToProductResponseDto(Product product) {

        return ProductResponseDto.builder()
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();
    }
}
