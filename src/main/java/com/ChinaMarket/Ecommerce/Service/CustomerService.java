package com.ChinaMarket.Ecommerce.Service;

import com.ChinaMarket.Ecommerce.Convertor.CustomerConvertor;
import com.ChinaMarket.Ecommerce.Model.Cart;
import com.ChinaMarket.Ecommerce.Model.Customer;
import com.ChinaMarket.Ecommerce.Repository.CustomerRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.CustomerRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.CustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public String addCustomer(CustomerRequestDto customerRequestDto) {

        Customer customer = CustomerConvertor.customerRequestDtoToCustomer(customerRequestDto);

        // Allote cart to customer
        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        //set cart in customer
        customer.setCart(cart);

        customerRepository.save(customer);
        return customer.getName()+"Congrats !! Welcome to China Market.";

    }
    public String findCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        return customer.getName();
    }

    public List<CustomerResponseDto> getALlCustomer() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();

        for(Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = CustomerConvertor.coustomerToCustomerResponseDto(customer);
            customerResponseDtoList.add(customerResponseDto);
        }
        return customerResponseDtoList;
    }

    public String customerDelete(int customerId) {
        customerRepository.deleteById(customerId);
        return "Customer deleted successfully.";
    }

    public String findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).getName();
    }
}
