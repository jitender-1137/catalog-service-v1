package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ResellerComplaintDto {
	private Long id;

	private Long resellerId;

	private Long complaintCategory;

	private String grievance;

	private String handelingTeam;

	private String action;


}
