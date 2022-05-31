package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class BucketSkuDto {
	private Long id;

	private Long bucketId;

	private Long productId;

	private Boolean activeForRanking;

	private Long bucketPreferredRank;


}
