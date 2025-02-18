package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.PaymentInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Orders orders;

	@Embedded
	private PaymentInfo paymentInfo;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime paymentTime;

}
