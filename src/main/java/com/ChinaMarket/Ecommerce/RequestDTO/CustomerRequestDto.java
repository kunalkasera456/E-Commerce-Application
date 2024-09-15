package com.ChinaMarket.Ecommerce.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {

    private String name;

    private int age;

    private String email;

    private String mobNo;
}
