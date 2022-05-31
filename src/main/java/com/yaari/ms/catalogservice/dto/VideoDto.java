package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class VideoDto {
	private Long id;

	private String title;

	private String url;

	private Long viewCount;

	private String category;

	private Long precedence;


}
