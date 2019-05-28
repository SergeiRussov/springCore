package org.russow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Collection<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "customers_orders",
            joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Collection<Order> orders;
}
