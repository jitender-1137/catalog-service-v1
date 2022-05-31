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
@Table(name = "rating_product")
@NoArgsConstructor
@Getter
@Setter
public class RatingProduct {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "reseller_id")
	private Long resellerId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "rating")
	private String rating;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

	@Column(name = "review")
	private String review;


}
