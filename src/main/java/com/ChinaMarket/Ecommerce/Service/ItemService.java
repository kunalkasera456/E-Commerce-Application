package com.ChinaMarket.Ecommerce.Service;

import com.ChinaMarket.Ecommerce.Exception.ProductNotFoundException;
import com.ChinaMarket.Ecommerce.Model.Item;
import com.ChinaMarket.Ecommerce.Model.Product;
import com.ChinaMarket.Ecommerce.Repository.ItemRepository;
import com.ChinaMarket.Ecommerce.Repository.ProductRepository;
import com.ChinaMarket.Ecommerce.ResponseDTO.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException {

        Product product;
        try{
            product = productRepository.findById(productId).get();
        }
        catch (Exception e){
            throw new ProductNotFoundException("Sorry! Invalid product id.");
        }

        Item item = Item.builder()
                .requiredQuantity(0)
                .product(product)
                .build();

        // map item to product
        product.setItem(item);

        // save both item and product
        productRepository.save(product);

        // prepare the response dto
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();

        return itemResponseDto;
    }
}
