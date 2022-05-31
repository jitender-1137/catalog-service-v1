package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pincodes_servicable")
@NoArgsConstructor
@Getter
@Setter
public class PincodesServicable {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "zipcode")
	private Long zipcode;

	@Column(name = "partner")
	private String partner;

	@Column(name = "process_code")
	private String processCode;

	@Column(name = "temporary_not_serviceable")
	private Boolean temporaryNotServiceable;


}
