package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
	private Long id;

	private Long resellerId;

	private String title;

	private String title2;

	private String body;


}
