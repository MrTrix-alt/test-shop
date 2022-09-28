package ru.simple.shop.task.testapp.item.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.simple.shop.task.testapp.item.dto.ItemProjections;
import ru.simple.shop.task.testapp.purchaseitem.entity.PurchaseItem;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@NoArgsConstructor
@Getter
@Setter
@NamedNativeQuery(name = "Item.findMostQuantityItem",
        query = "select it.name as name, count(*) as quantity from item it " +
                "join purchase_item ON purchase_item.item_id = it.id " +
                "group by it.id " +
                "order by quantity desc " +
                "limit 1",
        resultSetMapping = "Mapping.ItemProjections")
@NamedNativeQuery(name = "Item.findMostBoughtItemByCustomerAge",
        query = "select it.name, count(*) quantity from item it " +
                "join purchase_item pi ON pi.item_id = it.id " +
                "join purchase p on p.id = pi.purchase_id " +
                "join customer c on c.id = p.customer_id " +
                "where c.age = 18 " +
                "group by it.id " +
                "order by quantity desc " +
                "limit 1 ",
        resultSetMapping = "Mapping.ItemProjections")
@SqlResultSetMapping(name = "Mapping.ItemProjections",
        classes = @ConstructorResult(targetClass = ItemProjections.class,
                columns = {@ColumnResult(name = "name"),
                        @ColumnResult(name = "quantity")}))
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    @JsonManagedReference
    private Set<PurchaseItem> purchaseItems = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != 0 && id == item.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
