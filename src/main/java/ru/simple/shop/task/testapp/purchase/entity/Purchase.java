package ru.simple.shop.task.testapp.purchase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.simple.shop.task.testapp.customer.entity.Customer;
import ru.simple.shop.task.testapp.purchaseitem.entity.PurchaseItem;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @SequenceGenerator(name = "purchase_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "bought_at", nullable = false, updatable = false)
    private LocalDate boughtAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    public double getAmount() {
        double amount = 0.0d;
        for (PurchaseItem purchaseItem : purchaseItems) {
            amount += purchaseItem.getItem().getPrice();
        }
        return amount;
    }

    public String personName() {
        return customer.getName() + " " + customer.getLastName();
    }

    public int getCountItem() {
        return purchaseItems.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Purchase purchase = (Purchase) o;
        return id != 0 && id == purchase.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}



