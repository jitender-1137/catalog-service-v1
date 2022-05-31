package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ResellerDto {
	private Long id;

	private String emailId;

	private Boolean isActive;

	private String firstName;

	private String lastName;

	private String phoneNo;

	private String countryCode;

	private String securityImageUrl;

	private String credits;

	private String fullName;

	private String gender;

	private Long languageId;

	private String businessName;

	private String pinCode;

	private String city;

	private String state;

	private String bankAccountNumber;

	private String bankAccountName;

	private String bankIfsc;

	private String razorpayCustomerId;


	private Long customerId;

	private String ageSegment;

	private String occupation;

	private String profileImageUrl;


	private Boolean tutorialWatched;

	private String bankAccountImage;


}
