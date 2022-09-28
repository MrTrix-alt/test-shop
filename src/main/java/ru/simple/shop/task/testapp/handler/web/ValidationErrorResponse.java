package ru.simple.shop.task.testapp.handler.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ValidationErrorResponse {
    private int code;
    private String message;
    private List<FieldError> fieldErrors;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class FieldError {
        private String field;
        private String message;
    }
}
