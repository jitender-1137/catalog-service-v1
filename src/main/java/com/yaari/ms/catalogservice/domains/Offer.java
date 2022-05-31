package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "offer")
@NoArgsConstructor
@Getter
@Setter
public class Offer {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "product_sku_id")
	private Long productSkuId;

	@Column(name = "name")
	private String name;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;


}
