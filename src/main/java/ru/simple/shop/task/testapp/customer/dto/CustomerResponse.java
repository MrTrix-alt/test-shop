package ru.simple.shop.task.testapp.customer.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CustomerResponse {
    private String name;
    private String lastName;
}
