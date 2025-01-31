package com.ChinaMarket.Ecommerce.Service;

import com.ChinaMarket.Ecommerce.Enum.ProductStatus;
import com.ChinaMarket.Ecommerce.Exception.CustomerNotFoundException;
import com.ChinaMarket.Ecommerce.Exception.ProductNotFoundException;
import com.ChinaMarket.Ecommerce.Model.*;
import com.ChinaMarket.Ecommerce.Repository.CartRepository;
import com.ChinaMarket.Ecommerce.Repository.CustomerRepository;
import com.ChinaMarket.Ecommerce.Repository.ProductRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.OrderRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender emailSender;
    @Autowired
    CustomerRepository customerRepository;

    public String addToCart(OrderRequestDto orderRequestDto) throws Exception {

        Customer customer;
        try {
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch (Exception e) {
            throw new CustomerNotFoundException("Sorry invaliid customerId");
        }

        //2. step to check product is present or not
        Product product;
        try {
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch (Exception e) {
            throw new ProductNotFoundException("Sorry inaalid productId");
        }

        //3. step to check quamtity avilable for purches or not
        if(orderRequestDto.getRequiredQuantity() > product.getQuantity()) {
            throw new Exception("Sorry! Required quantity is not avilable");
        }

        Cart cart = customer.getCart();

        int newCost = cart.getCartTotal() + orderRequestDto.getRequiredQuantity() * product.getPrice();
        cart.setCartTotal(newCost);

        //Add Item to current cart
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);
        cart.getItems().add(item);

        customerRepository.save(customer);

        return "Item has been added to tpur cart!!";
    }

    public List<OrderResponseDto> checkout(int customerId) throws Exception {

        Customer customer;
        try {
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e) {
            throw new CustomerNotFoundException("Sorry invaliid customerId");
        }

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        int totalCost = customer.getCart().getCartTotal();
        Cart cart = customer.getCart();
        for(Item item: cart.getItems()){
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity()*item.getProduct().getPrice());
            order.setDeliveryCharge(0);
            order.setCustomer(customer);
            order.getOrderedItems().add(item);

            Card card = customer.getCards().get(0);
            String cardNo = "";
            for(int i=0;i<card.getCardNo().length()-4;i++)
                cardNo += 'X';
            cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
            order.setCardUsedForPayment(cardNo);

            int leftQuantity = item.getProduct().getQuantity()-item.getRequiredQuantity();
            if(leftQuantity<=0)
                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
            item.getProduct().setQuantity(leftQuantity);

            customer.getOrders().add(order);

            // prepare response dto
            // prepare response dto
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productName(item.getProduct().getProductName())
                    .orderDate(order.getOrderDate())
                    .quantityOrdered(item.getRequiredQuantity())
                    .cardUsedForPayment(cardNo)
                    .itemPrice(item.getProduct().getPrice())
                    .totalCost(order.getTotalCost())
                    .deliveryCharge(0)
                    .build();

            orderResponseDtos.add(orderResponseDto);
        }

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);

        String text = "Congrats your order with total value "+totalCost+" has been placed";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendemailsenderapi@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order Placed from China Market");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDtos;
    }
}
