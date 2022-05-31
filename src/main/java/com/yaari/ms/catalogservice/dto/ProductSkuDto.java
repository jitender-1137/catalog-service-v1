package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ProductSkuDto {
	private Long id;

	private String skuId;

	private String productName;

	private String brand;

	private String description;

	private String materialCare;

	private double discount;

	private java.sql.Date discountStartDate;

	private java.sql.Date discountEndDate;

	private java.sql.Date manufacturingDate;

	private String countryOfOrigin;

	private String keyFeatures;

	private String videoUrl;

	private java.sql.Date reStockDate;

	private String guarantee;

	private String warranty;

	private Long mrp;

	private Long sp;

	private Long inventory;

	private String specifications;

	private Long productCatalogId;

	private Boolean nextDayDispatch;

	private Long viewed;

	private Long shared;

	private Long sale;

	private Long searched;

	private Long clicked;


	private String rating;

	private Long numOfVotes;

	private Long qualityRating;

	private Long qualityScore;

	private String breadth;

	private String length;

	private String weight;

	private String height;

	private String comment;

	private Long groupId;

	private Long productId;

	private String hsnCode;

	private String offers;

	private Long productCode;

	private Boolean edited;


	private Boolean activeForRanking;

	private Long preferredRank;

	private String returnExchangeFlag;

	private Boolean isCod;


}
