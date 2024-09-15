package com.ChinaMarket.Ecommerce.Service;


import com.ChinaMarket.Ecommerce.Convertor.ProductConvertor;
import com.ChinaMarket.Ecommerce.Enum.ProductCategory;
import com.ChinaMarket.Ecommerce.Exception.SellerNotFoundException;
import com.ChinaMarket.Ecommerce.Model.Product;
import com.ChinaMarket.Ecommerce.Model.Seller;
import com.ChinaMarket.Ecommerce.Repository.ProductRepository;
import com.ChinaMarket.Ecommerce.Repository.SellerRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.ProductRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDTO) throws Exception{

//    1. step to check seller present or not
        Seller seller;

        try {
            seller = sellerRepository.findById(productRequestDTO.getSellerId()).get();
        }
        catch (Exception e) {
            throw new SellerNotFoundException("Invalid Seller Id");
        }
//     2. step to check product present or not
        Product product = ProductConvertor.productRequestDtotoProduct(productRequestDTO);

        product.setSeller(seller);
        seller.getProducts().add(product);

        //saved the seller and paoduct
        sellerRepository.save(seller);   //seller is parent

        // convert product to responce dto
        //prepare response
        ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);
        return productResponseDto;

    }


    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory productCategory) {

        List<Product> products = productRepository.findAllByProductCategory(productCategory);

        // prepare a list of response dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }
}
