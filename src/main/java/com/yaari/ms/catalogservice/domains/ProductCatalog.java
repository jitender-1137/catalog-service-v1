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
@Table(name = "product_catalog")
@NoArgsConstructor
@Getter
@Setter
public class ProductCatalog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "supplier_id")
	private Long supplierId;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "approved")
	private Boolean approved;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "viewed")
	private Long viewed;

	@Column(name = "shared")
	private Long shared;

	@Column(name = "catalog_name")
	private String catalogName;

	@Column(name = "comment")
	private String comment;

	@Column(name = "pending_with")
	private String pendingWith;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
