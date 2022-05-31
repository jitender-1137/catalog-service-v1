package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrderItemStatusLogDto {
	private Long id;

	private Long orderId;

	private String ucOrderId;

	private String awbNumber;


	private String status;

	private String source;


}
