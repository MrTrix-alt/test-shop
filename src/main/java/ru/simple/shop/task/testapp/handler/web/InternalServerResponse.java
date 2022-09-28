package ru.simple.shop.task.testapp.handler.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InternalServerResponse {
    private int code;
    private String message;
}
