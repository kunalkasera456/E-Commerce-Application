package com.ChinaMarket.Ecommerce.Convertor;

import com.ChinaMarket.Ecommerce.Model.Customer;
import com.ChinaMarket.Ecommerce.RequestDTO.CustomerRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.CustomerResponseDto;

public class CustomerConvertor {

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto) {

        return Customer.builder()
                .name(customerRequestDto.getName())
                .age(customerRequestDto.getAge())
                .email(customerRequestDto.getEmail())
                .mobNo(customerRequestDto.getMobNo())
                .build();

    }

    public static CustomerResponseDto coustomerToCustomerResponseDto(Customer customer) {

        return CustomerResponseDto.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobNo(customer.getMobNo())
                .build();
    }
}
