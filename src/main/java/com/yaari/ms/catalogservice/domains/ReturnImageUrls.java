package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "return_image_urls")
@NoArgsConstructor
@Getter
@Setter
public class ReturnImageUrls {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "return_id")
	private Long returnId;

	@Column(name = "image_url")
	private String imageUrl;


}
