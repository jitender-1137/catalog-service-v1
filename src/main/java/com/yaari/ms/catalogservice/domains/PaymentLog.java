package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "payment_log")
@NoArgsConstructor
@Getter
@Setter
public class PaymentLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "razor_pay_order_id")
	private String razorPayOrderId;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
