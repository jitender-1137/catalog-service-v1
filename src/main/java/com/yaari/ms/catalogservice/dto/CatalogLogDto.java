package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CatalogLogDto {
	private Long id;

	private Long catalogId;

	private Long actionBy;

	private long actionTime;

	private String action;

	private Boolean cycleCompleted;


}
