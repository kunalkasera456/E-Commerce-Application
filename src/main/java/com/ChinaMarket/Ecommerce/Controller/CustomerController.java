package com.ChinaMarket.Ecommerce.Controller;


import com.ChinaMarket.Ecommerce.Model.Customer;
import com.ChinaMarket.Ecommerce.RequestDTO.CustomerRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.CustomerResponseDto;
import com.ChinaMarket.Ecommerce.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return  customerService.addCustomer(customerRequestDto);
    }

    @GetMapping("/find_by_id")
    public String findCustomerById(@RequestParam("customerId") int customerId) {
        return customerService.findCustomerById(customerId);
    }

    @GetMapping("/get/customers")
    public List<CustomerResponseDto> getALlCustomer() {
        return customerService.getALlCustomer();
    }

    @DeleteMapping("delete")
    public String customerDelete(@RequestParam int customerId) {
        return customerService.customerDelete(customerId);
    }

    @GetMapping("/finf_by_email")
    public String findCustomerByEmail(@RequestParam("customerId") String email) {
        return customerService.findCustomerByEmail(email);
    }
}
