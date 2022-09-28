package ru.simple.shop.task.testapp.shoppingreport.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.simple.shop.task.testapp.customer.dto.CustomerProjection;
import ru.simple.shop.task.testapp.customer.dto.CustomerResponse;
import ru.simple.shop.task.testapp.item.dto.ItemProjections;
import ru.simple.shop.task.testapp.item.dto.ItemResponse;
import ru.simple.shop.task.testapp.shoppingreport.dto.ShoppingReportResponse;
import ru.simple.shop.task.testapp.shoppingreport.service.ShoppingReportService;

import java.math.BigInteger;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

// Хорошо было бы написать тесты с поднятием контекста в тестах
// для проверки БД с помощью залива тестовых данных через аннотацию @SQL
// и для Controller написать тесты для проверки ендпоинтов
@RunWith(MockitoJUnitRunner.class)
class ShoppingReportControllerUT {

    private ShoppingReportController shoppingReportController;
    private ShoppingReportService shoppingReportService;

    @BeforeEach
    public void setUp() {
        shoppingReportService = Mockito.mock(ShoppingReportService.class);
        shoppingReportController = new ShoppingReportController(shoppingReportService);
    }

    @Test
    void findByLastWeek() {
        ShoppingReportResponse shoppingReportResponse = new ShoppingReportResponse();
        shoppingReportResponse.setAmount(11111);
        shoppingReportResponse.setQuantity(2);
        Clock clock = Clock.fixed(LocalDate.of(2022,1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        shoppingReportResponse.setBoughtAt(LocalDate.now(clock));
        shoppingReportResponse.setItems(List.of(new ItemResponse("item")));
        shoppingReportResponse.setCustomer(new CustomerResponse("name", "lastName"));

        when(shoppingReportService.findPurchasesByLastWeek()).thenReturn(List.of(shoppingReportResponse));
        assertThat(shoppingReportController.findByLastWeek().getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getMostBoughtItem() {
        when(shoppingReportService.findMostBoughtItem()).thenReturn(new ItemProjections("name", BigInteger.ONE));
        assertThat(shoppingReportController.findMostBoughtItem().getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void findCustomerWhoBoughtMostThanOther() {
        when(shoppingReportService.findCustomerWhoBoughtMostThanOther()).thenReturn(new CustomerProjection("name", "lastName", BigInteger.ONE));
        assertThat(shoppingReportController.findMostBoughtItem().getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void findMostBoughtItemByCustomerAge() {
        when(shoppingReportService.findMostBoughtItemByCustomerAge(18)).thenReturn(new ItemProjections("name", BigInteger.ONE));
        assertThat(shoppingReportController.findMostBoughtItem().getStatusCodeValue()).isEqualTo(200);
    }
}