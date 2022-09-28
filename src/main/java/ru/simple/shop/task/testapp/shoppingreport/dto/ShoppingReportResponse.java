package ru.simple.shop.task.testapp.shoppingreport.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simple.shop.task.testapp.customer.dto.CustomerResponse;
import ru.simple.shop.task.testapp.item.dto.ItemResponse;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ShoppingReportResponse {


    private CustomerResponse customer;
    private List<ItemResponse> items;
    private LocalDate boughtAt;
    private int quantity;
    private double amount;
}
