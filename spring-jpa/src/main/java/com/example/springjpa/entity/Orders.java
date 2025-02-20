package com.example.springjpa.entity;

import com.example.springjpa.domain.OrdersStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setOrdersStatus(OrdersStatus ordersStatus) {
		this.ordersStatus = ordersStatus;
	}

	public void setOrdersItems(List<OrdersItem> ordersItems) {
		this.ordersItems = ordersItems;
	}

}
