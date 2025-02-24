package com.example.springjpa.entity;

import com.example.springjpa.domain.OrdersStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	@OneToMany(
			mappedBy = "orders",
			fetch = FetchType.LAZY,
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private List<OrdersItem> ordersItems = new ArrayList<>();

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime ordersTime;

	@Enumerated(EnumType.STRING)
	private OrdersStatus ordersStatus;

	public Orders() {
		this.ordersStatus = OrdersStatus.PENDING;
	}

	public void assignCustomer(Customer customer) {
		this.customer = customer;
	}

	public void updateOrdersStatus(OrdersStatus ordersStatus) {
		this.ordersStatus = ordersStatus;
	}

	public void addOrdersItems(OrdersItem ordersItem) {
		Objects.requireNonNull(ordersItem, "Orders Item must be not null");
		this.ordersItems.add(ordersItem);
		ordersItem.setOrders(this);
	}

}
