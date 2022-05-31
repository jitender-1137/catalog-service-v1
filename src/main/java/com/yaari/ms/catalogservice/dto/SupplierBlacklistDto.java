package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SupplierBlacklistDto {
	private Long id;

	private String emailId;

	private String panNo;

	private String gstNo;

	private Long attempts;

	private Boolean blacklist;

	private String bankAccountName;

	private String bankAccountNumber;

	private String bankIfsc;

	private String bankName;


}
