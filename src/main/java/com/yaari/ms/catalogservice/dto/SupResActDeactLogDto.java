package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SupResActDeactLogDto {
	private Long id;

	private Long adLogId;

	private String userType;

	private String mobileNo;

	private String status;

	private String uploadStatus;


	private String supCatId;

	private Long supCatCount;


}
