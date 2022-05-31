package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "order")
@NoArgsConstructor
@Getter
@Setter
public class Order {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "uc_id")
	private String ucId;

	@Column(name = "reseller_id")
	private Long resellerId;

	@Column(name = "status")
	private String status;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "total_tax")
	private double totalTax;

	@Column(name = "shipping_charges")
	private double shippingCharges;

	@Column(name = "total_discount")
	private double totalDiscount;

	@Column(name = "total_reseller_margin")
	private double totalResellerMargin;

	@Column(name = "address_id")
	private Long addressId;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	private long updatedTime;

	@Column(name = "payment_time")
	private long paymentTime;

	@Column(name = "cancellable_time")
	private long cancellableTime;


}
