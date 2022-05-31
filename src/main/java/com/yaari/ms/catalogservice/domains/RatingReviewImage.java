package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating_review_image")
@NoArgsConstructor
@Getter
@Setter
public class RatingReviewImage {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "rating_id")
	private Long ratingId;

	@Column(name = "url")
	private String url;


}
