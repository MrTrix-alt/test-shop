package ru.simple.shop.task.testapp.shoppingreport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simple.shop.task.testapp.customer.entity.Customer;
import ru.simple.shop.task.testapp.customer.dto.CustomerProjection;
import ru.simple.shop.task.testapp.customer.repo.CustomerRepository;
import ru.simple.shop.task.testapp.customer.dto.CustomerResponse;
import ru.simple.shop.task.testapp.exception.ReportNotFoundException;
import ru.simple.shop.task.testapp.item.dto.ItemResponse;
import ru.simple.shop.task.testapp.item.repo.ItemRepository;
import ru.simple.shop.task.testapp.purchase.entity.Purchase;
import ru.simple.shop.task.testapp.purchase.repo.PurchaseRepository;
import ru.simple.shop.task.testapp.item.dto.ItemProjections;
import ru.simple.shop.task.testapp.purchaseitem.entity.PurchaseItem;
import ru.simple.shop.task.testapp.shoppingreport.dto.ShoppingReportResponse;
import ru.simple.shop.task.testapp.util.DateUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShoppingReportService {
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final PurchaseRepository purchaseRepository;
    private final Clock clock;

    public ItemProjections findMostBoughtItem() {
        ItemProjections mostQuantityItem = itemRepository.findMostQuantityItem();
        if (Objects.isNull(mostQuantityItem)) {
            throw new ReportNotFoundException("Данных по самому продаваемому товару нет");
        }
        return mostQuantityItem;
    }

    public CustomerProjection findCustomerWhoBoughtMostThanOther() {
        CustomerProjection customerWhoBoughtMostThanOther =
                customerRepository.customerWhoBoughtMostThanOtherByLastHalfYear(
                        LocalDate.ofInstant(DateUtil.getSixMonthAgo(),
                        ZoneId.systemDefault()), LocalDate.now(clock)
                );
        if (Objects.isNull(customerWhoBoughtMostThanOther)) {
            throw new ReportNotFoundException("Данных по пользователя с наибольшим количество покупок нет");
        }
        return customerWhoBoughtMostThanOther;
    }

    public ItemProjections findMostBoughtItemByCustomerAge(int age) {
        ItemProjections mostBoughtItemByCustomerAge = itemRepository.findMostBoughtItemByCustomerAge(age);
        if (Objects.isNull(mostBoughtItemByCustomerAge)) {
            throw new ReportNotFoundException(String.format("Нет данных по самому покупаемому товару среди пользователей возрастом %s", age));
        }
        return mostBoughtItemByCustomerAge;
    }

    public List<ShoppingReportResponse> findPurchasesByLastWeek() {
        Set<Purchase> allByLastWeek = purchaseRepository.findAllByLastWeek(
                LocalDate.ofInstant(DateUtil.getSevenDayAgo(), ZoneId.systemDefault()),
                LocalDate.now(clock)
        );
        if (allByLastWeek.isEmpty()) {
            throw new ReportNotFoundException("Нет данных по покупкам за последнюю неделю");
        }
        return allByLastWeek.stream()
                .map(ShoppingReportService::apply)
                .collect(Collectors.toList());
    }
    private static ShoppingReportResponse apply(Purchase purchase) {
        ShoppingReportResponse shoppingReportResponse = new ShoppingReportResponse();
        shoppingReportResponse.setBoughtAt(purchase.getBoughtAt());
        CustomerResponse customerResponse = new CustomerResponse();
        Customer customer = purchase.getCustomer();
        customerResponse.setName(customer.getName());
        customerResponse.setLastName(customer.getLastName());
        shoppingReportResponse.setCustomer(customerResponse);
        List<ItemResponse> items = new ArrayList<>();
        List<PurchaseItem> purchaseItems = purchase.getPurchaseItems();
        for (PurchaseItem purchaseItem : purchaseItems) {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setName(purchaseItem.getItem().getName());
            items.add(itemResponse);
        }
        shoppingReportResponse.setItems(items);
        shoppingReportResponse.setQuantity(purchase.getCountItem());
        shoppingReportResponse.setAmount(purchase.getAmount());
        return shoppingReportResponse;
    }

}
