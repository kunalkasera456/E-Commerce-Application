package com.ChinaMarket.Ecommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {

    private String name;

    private int age;

    private String email;

    private String mobNo;
}
