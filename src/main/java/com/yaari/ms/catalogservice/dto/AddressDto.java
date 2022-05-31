package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
	private Long id;

	private String fullName;

	private String phoneNo;

	private String lineOne;

	private String lineTwo;

	private String pinCode;

	private String state;

	private String city;

	private String country;

	private String email;

	private String landmark;

	private String type;

	private Boolean orderAddress;

	private String shiprocketPickupLocId;

}
