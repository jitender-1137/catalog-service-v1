package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ProductCatalogDto {
	private Long id;

	private Long supplierId;

	private Long categoryId;

	private Boolean active;

	private Boolean approved;


	private Long viewed;

	private Long shared;

	private String catalogName;

	private String comment;

	private String pendingWith;


}
