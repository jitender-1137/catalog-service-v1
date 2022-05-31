package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class BucketItemDto {
	private Long id;

	private Long bucketId;

	private String bucketType;

	private String sortKey;

	private String sortValue;

	private String analyticKey;

	private String analyticValue;

	private String filterKey;

	private String filterValue;

	private String specificationKey;

	private String specificationValue;


}
