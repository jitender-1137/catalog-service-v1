package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AdminUserDto {
	private String emailId;

	private String hashedPassword;

	private Boolean isActive;

	private Long id;

	private String adminRole;

	private String adminDesignation;

	private String firstName;

	private String lastName;

	private String phoneNo;

	private int resetTokenExpiry;


}
