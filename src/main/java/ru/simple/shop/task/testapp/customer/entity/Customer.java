package ru.simple.shop.task.testapp.customer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.simple.shop.task.testapp.customer.dto.CustomerProjection;
import ru.simple.shop.task.testapp.purchase.entity.Purchase;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
@NamedNativeQuery(name = "Customer.customerWhoBoughtMostThanOtherByLastHalfYear",
        query = "select c.name as name, c.last_name as lastName, count(*) as quantity from customer c " +
                "join purchase p on p.customer_id = c.id " +
                "where p.bought_at between :sixMonthsAgo and :currenDate " +
                "group by c.id " +
                "order by quantity desc " +
                "limit 1",
        resultSetMapping = "Mapping.CustomerProjection")
@SqlResultSetMapping(name = "Mapping.CustomerProjection",
        classes = @ConstructorResult(targetClass = CustomerProjection.class,
                columns = {@ColumnResult(name = "name"),
                        @ColumnResult(name = "lastName"),
                        @ColumnResult(name = "quantity")}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Purchase> purchases = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return Objects.nonNull(id) && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
