package ru.simple.shop.task.testapp.purchaseitem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.simple.shop.task.testapp.item.entity.Item;
import ru.simple.shop.task.testapp.purchase.entity.Purchase;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "purchase_item")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseitem_seq")
    @SequenceGenerator(name = "purchaseitem_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchaseItem otherPurchaseItem = (PurchaseItem) o;
        return id != 0 && id == otherPurchaseItem.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}