package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SupplierComplaintDto {
	private Long id;

	private Long supplierId;

	private Long complaintCategory;

	private String grievance;

	private String handelingTeam;

	private String action;


}
