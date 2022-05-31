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
@Table(name = "product_sku_image")
@NoArgsConstructor
@Getter
@Setter
public class ProductSkuImage {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "watermarked_image_url")
	private String watermarkedImageUrl;

	@Column(name = "thumbnail_generated")
	private Boolean thumbnailGenerated;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
