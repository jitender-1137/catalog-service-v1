package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
public class Address {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "line_one")
	private String lineOne;

	@Column(name = "line_two")
	private String lineTwo;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "email")
	private String email;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "type")
	private String type;

	@Column(name = "order_address")
	private Boolean orderAddress;

	@Column(name = "shiprocket_pickup_loc_id")
	private String shiprocketPickupLocId;

}
