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
@Table(name = "product_sku")
@NoArgsConstructor
@Getter
@Setter
public class ProductSku {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "sku_id")
	private String skuId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "brand")
	private String brand;

	@Column(name = "description")
	private String description;

	@Column(name = "material_care")
	private String materialCare;

	@Column(name = "discount")
	private double discount;

	@Column(name = "discount_start_date")
	private java.sql.Date discountStartDate;

	@Column(name = "discount_end_date")
	private java.sql.Date discountEndDate;

	@Column(name = "manufacturing_date")
	private java.sql.Date manufacturingDate;

	@Column(name = "country_of_origin")
	private String countryOfOrigin;

	@Column(name = "key_features")
	private String keyFeatures;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "re_stock_date")
	private java.sql.Date reStockDate;

	@Column(name = "guarantee")
	private String guarantee;

	@Column(name = "warranty")
	private String warranty;

	@Column(name = "mrp")
	private Long mrp;

	@Column(name = "sp")
	private Long sp;

	@Column(name = "inventory")
	private Long inventory;

	@Column(name = "specifications")
	private String specifications;

	@Column(name = "product_catalog_id")
	private Long productCatalogId;

	@Column(name = "next_day_dispatch")
	private Boolean nextDayDispatch;

	@Column(name = "viewed")
	private Long viewed;

	@Column(name = "shared")
	private Long shared;

	@Column(name = "sale")
	private Long sale;

	@Column(name = "searched")
	private Long searched;

	@Column(name = "clicked")
	private Long clicked;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "rating")
	private String rating;

	@Column(name = "num_of_votes")
	private Long numOfVotes;

	@Column(name = "quality_rating")
	private Long qualityRating;

	@Column(name = "quality_score")
	private Long qualityScore;

	@Column(name = "breadth")
	private String breadth;

	@Column(name = "length")
	private String length;

	@Column(name = "weight")
	private String weight;

	@Column(name = "height")
	private String height;

	@Column(name = "comment")
	private String comment;

	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "hsn_code")
	private String hsnCode;

	@Column(name = "offers")
	private String offers;

	@Column(name = "product_code")
	private Long productCode;

	@Column(name = "edited")
	private Boolean edited;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

	@Column(name = "active_for_ranking")
	private Boolean activeForRanking;

	@Column(name = "preferred_rank")
	private Long preferredRank;

	@Column(name = "return_exchange_flag")
	private String returnExchangeFlag;

	@Column(name = "is_cod")
	private Boolean isCod;


}
