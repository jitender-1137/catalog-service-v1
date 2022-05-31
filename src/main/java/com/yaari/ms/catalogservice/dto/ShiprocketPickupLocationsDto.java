package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ShiprocketPickupLocationsDto {
	private Long id;

	private String pickupLocName;

	private String pickupLocId;

	private String address;

	private String supplierEmail;


}
