package ru.simple.shop.task.testapp.shoppingreport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simple.shop.task.testapp.customer.dto.CustomerProjection;
import ru.simple.shop.task.testapp.item.dto.ItemProjections;
import ru.simple.shop.task.testapp.shoppingreport.dto.ShoppingReportResponse;
import ru.simple.shop.task.testapp.shoppingreport.service.ShoppingReportService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/shoppingreport", produces = {MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class ShoppingReportController {
    private final ShoppingReportService shoppingReportService;

    @GetMapping(value = "/byLastWeek")
    public ResponseEntity<List<ShoppingReportResponse>> findByLastWeek() {
        return new ResponseEntity<>(shoppingReportService.findPurchasesByLastWeek(), HttpStatus.OK);
    }

    @GetMapping(value = "/mostBoughtItem")
    public ResponseEntity<ItemProjections> findMostBoughtItem() {
        return new ResponseEntity<>(shoppingReportService.findMostBoughtItem(), HttpStatus.OK);
    }

    @GetMapping(value = "/customerWhoBoughtMostThanOther")
    public ResponseEntity<CustomerProjection> findCustomerWhoBoughtMostThanOther() {
        return new ResponseEntity<>(shoppingReportService.findCustomerWhoBoughtMostThanOther(), HttpStatus.OK);
    }

    @GetMapping(value = "/findMostBoughtItemByCustomerAge/{age}")
    public ResponseEntity<ItemProjections> findMostBoughtItemByCustomerAge (
            @Min(value = 16, message = "Возраст должен быть не меньше 16")
            @Max(value = 120, message = "Возраст должен быть не больше 120")
            @PathVariable("age") int age)
    {
        return new ResponseEntity<>(shoppingReportService.findMostBoughtItemByCustomerAge(age), HttpStatus.OK);
    }
}
