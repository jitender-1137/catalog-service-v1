package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ProductLogDto {
	private Long id;

	private Long productId;

	private Long modifiedBy;

	private long modifiedTime;

	private Long catalogLogId;


	private String productName;

	private String description;

	private String materialCare;

	private String countryOfOrigin;

	private String keyFeatures;

	private String guarantee;

	private String warranty;

	private Long mrp;

	private Long sp;

	private Long inventory;

	private String specifications;

	private String productImg;

	private String deletedImg;

	private String videoUrl;

	private String deletedVideo;


}
