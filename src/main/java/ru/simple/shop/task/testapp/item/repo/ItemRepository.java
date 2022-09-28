package ru.simple.shop.task.testapp.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simple.shop.task.testapp.item.entity.Item;
import ru.simple.shop.task.testapp.item.dto.ItemProjections;

public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query(nativeQuery = true)
    ItemProjections findMostQuantityItem();

    @Query(nativeQuery = true)
    ItemProjections findMostBoughtItemByCustomerAge(int age);
}
