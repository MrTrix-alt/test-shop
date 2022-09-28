package ru.simple.shop.task.testapp.purchaseitem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simple.shop.task.testapp.purchaseitem.entity.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {



}
