package ru.simple.shop.task.testapp.purchase.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simple.shop.task.testapp.purchase.entity.Purchase;

import java.time.LocalDate;
import java.util.Set;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value =  "from Purchase p left join fetch p.customer left join fetch p.purchaseItems where p.boughtAt between :beginWeekDate and :currentDate")
    Set<Purchase> findAllByLastWeek(@Param("beginWeekDate") LocalDate beginWeekDate, @Param("currentDate") LocalDate currentDate);

}
