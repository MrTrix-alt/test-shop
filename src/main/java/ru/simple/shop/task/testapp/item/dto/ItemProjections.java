package ru.simple.shop.task.testapp.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@AllArgsConstructor
@Getter
@Setter
public class ItemProjections {
    private String name;
    @JsonIgnore
    private BigInteger quantity;
}
