package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class RatingReviewImageDto {
	private Long id;

	private Long ratingId;

	private String url;


}
