package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@Getter
@Setter
public class Payment {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "razor_pay_payment_id")
	private String razorPayPaymentId;

	@Column(name = "razor_pay_order_id")
	private String razorPayOrderId;

	@Column(name = "redirect_url")
	private String redirectUrl;

	@Column(name = "payment_method")
	private String paymentMethod;


}
