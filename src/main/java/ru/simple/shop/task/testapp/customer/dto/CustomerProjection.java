package ru.simple.shop.task.testapp.customer.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@AllArgsConstructor
@Getter
@Setter
public class CustomerProjection {

    private String name;
    private String lastName;
    @JsonIgnore
    private BigInteger quantity;

}
