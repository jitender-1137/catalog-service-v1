package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bucket_item")
@NoArgsConstructor
@Getter
@Setter
public class BucketItem {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "bucket_id")
	private Long bucketId;

	@Column(name = "bucket_type")
	private String bucketType;

	@Column(name = "sort_key")
	private String sortKey;

	@Column(name = "sort_value")
	private String sortValue;

	@Column(name = "analytic_key")
	private String analyticKey;

	@Column(name = "analytic_value")
	private String analyticValue;

	@Column(name = "filter_key")
	private String filterKey;

	@Column(name = "filter_value")
	private String filterValue;

	@Column(name = "specification_key")
	private String specificationKey;

	@Column(name = "specification_value")
	private String specificationValue;


}
