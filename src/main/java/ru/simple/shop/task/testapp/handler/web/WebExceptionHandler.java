package ru.simple.shop.task.testapp.handler.web;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.simple.shop.task.testapp.exception.ReportNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Hidden
@Slf4j
@RestControllerAdvice
@RequestMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class WebExceptionHandler {


    @ExceptionHandler(value = {ReportNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ReportErrorResponse handleReportException(ReportNotFoundException reportNotFoundException,
                                                     HttpServletRequest httpServletRequest) {
        log.error("При работе с отчетом по запросу \"{}\" произошла ошибка: \"{}\"", httpServletRequest.getRequestURI(), reportNotFoundException.getMessage());
        ReportErrorResponse reportErrorResponse = new ReportErrorResponse();
        reportErrorResponse.setCode(HttpStatus.NOT_FOUND.value());
        reportErrorResponse.setMessage(reportNotFoundException.getMessage());
        return reportErrorResponse;
    }

    // Обертка над Exception, если где-то произойдет исключение
    @ExceptionHandler(value = {ApplicationWrapperException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalServerResponse handleApplicationWrapperException(Exception e,
                                                  HttpServletRequest httpServletRequest) {
        log.error("При выполнении запроса \"{}\" произошла ошибка: \"{}\"", httpServletRequest.getRequestURI(), e.getMessage());
        InternalServerResponse internalServerResponse = new InternalServerResponse();
        internalServerResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        internalServerResponse.setMessage(e.getMessage());
        return internalServerResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationExceptions(
            ConstraintViolationException constraintViolationException) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setMessage("В запросе имеются некорректные параметры");
        validationErrorResponse.setFieldErrors(constraintViolationException.getConstraintViolations().stream()
                .map(error -> new ValidationErrorResponse.FieldError(error.getPropertyPath().toString(), error.getMessage()))
                .collect(Collectors.toList()));
        return validationErrorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setMessage("В запросе имеются некорректные параметры");
        validationErrorResponse.setFieldErrors(methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationErrorResponse.FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList()));
        return validationErrorResponse;
    }
}
