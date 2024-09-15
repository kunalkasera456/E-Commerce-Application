package com.ChinaMarket.Ecommerce.Service;

import com.ChinaMarket.Ecommerce.Enum.ProductStatus;
import com.ChinaMarket.Ecommerce.Exception.CustomerNotFoundException;
import com.ChinaMarket.Ecommerce.Exception.ProductNotFoundException;
import com.ChinaMarket.Ecommerce.Model.*;
import com.ChinaMarket.Ecommerce.Repository.CustomerRepository;
import com.ChinaMarket.Ecommerce.Repository.ProductRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.OrderRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender emailSender;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception {

        // 1. step to check customer is present or not
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

        // create order
        Ordered order = new Ordered();
//        order.setTotalCost(orderRequestDto.getRequiredQuantity()* product.getPrice());
//        order.setDeliveryCharge(40);

        int total = orderRequestDto.getRequiredQuantity() * product.getQuantity();
        order.setTotalCost(total);
        int deleveryCharge = 0;
        if(total < 500) {
            deleveryCharge = 40;
        }
        order.setDeliveryCharge(deleveryCharge);

        //select card for payment
        Card card = customer.getCards().get(0);  //assume ki customer pehla card use kar raha hai
        String cardNo = "";
        for(int i=0; i<card.getCardNo().length()-4; i++) {
            cardNo = cardNo + 'X';
        }
        cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
        order.setCardUsedForPayment(cardNo);

        // set the item
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);
        item.setOrder(order);
        order.getOrderedItems().add(item);
        order.setCustomer(customer);  //save the parent

        //updat the database of quanyity
        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0) {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(leftQuantity);

        customer.getOrders().add(order);   // add customer's orderlist
        Customer savedCustomer = customerRepository.save(customer);
        Ordered saveOrder = savedCustomer.getOrders().get(savedCustomer.getOrders().size()-1);

        //prepare response DTO
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getProductName())
                .orderDate(saveOrder.getOrderDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .cardUsedForPayment(cardNo)
                .itemPrice(product.getPrice())
                .totalCost(order.getTotalCost())
                .deliveryCharge(deleveryCharge)
                .build();

         // send an email
        String text = "Congrats your order with total value "+order.getTotalCost()+" has been placed";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendemailsenderapi@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order Placed Notification");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDto;


    }

}
