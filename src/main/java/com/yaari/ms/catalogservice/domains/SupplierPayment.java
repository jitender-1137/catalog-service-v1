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
@Table(name = "supplier_payment")
@NoArgsConstructor
@Getter
@Setter
public class SupplierPayment {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "order_item_id")
	private Long orderItemId;

	@Column(name = "status")
	private String status;

	@Column(name = "discard_reason")
	private String discardReason;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "settlement_type_")
	private String settlementType;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
