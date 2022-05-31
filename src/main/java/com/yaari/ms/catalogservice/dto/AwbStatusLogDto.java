package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AwbStatusLogDto {
	private Long id;

	private String awbNumber;

	private String statusCode;

	private String partner;

	private String source;


}
