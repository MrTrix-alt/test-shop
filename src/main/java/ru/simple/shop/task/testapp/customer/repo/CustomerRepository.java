package ru.simple.shop.task.testapp.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simple.shop.task.testapp.customer.entity.Customer;
import ru.simple.shop.task.testapp.customer.dto.CustomerProjection;

import java.time.LocalDate;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(nativeQuery = true)
    CustomerProjection customerWhoBoughtMostThanOtherByLastHalfYear(@Param("sixMonthsAgo") LocalDate sixMonthsAgo,
                                                                    @Param("currenDate") LocalDate currenDate);
}
