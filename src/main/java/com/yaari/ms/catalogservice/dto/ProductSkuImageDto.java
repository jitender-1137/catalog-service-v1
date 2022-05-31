package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ProductSkuImageDto {
	private Long id;

	private String url;

	private Long productId;

	private String watermarkedImageUrl;

	private Boolean thumbnailGenerated;


}
