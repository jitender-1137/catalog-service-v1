package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "product_log")
@NoArgsConstructor
@Getter
@Setter
public class ProductLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_time")
	private long modifiedTime;

	@Column(name = "catalog_log_id")
	private Long catalogLogId;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "description")
	private String description;

	@Column(name = "material_care")
	private String materialCare;

	@Column(name = "country_of_origin")
	private String countryOfOrigin;

	@Column(name = "key_features")
	private String keyFeatures;

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

	@Column(name = "product_img")
	private String productImg;

	@Column(name = "deleted_img")
	private String deletedImg;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "deleted_video")
	private String deletedVideo;


}
