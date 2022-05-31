package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SupplierDto {
	private String emailId;

	private String hashedPassword;

	private Boolean isActive;

	private Long id;

	private String namePanCard;

	private String contactPerson;

	private String phoneNo;

	private String city;

	private String state;

	private Boolean hasGst;

	private String gstNo;

	private String panNo;

	private String type;

	private Long priceRangeMin;

	private Long priceRangeMax;

	private Long averageMonthlyStock;

	private String referrer;

	private Long addressId;

	private Long primaryCategoryId;

	private Long onboarderId;

	private Boolean approved;

	private String bankAccountName;

	private String bankAccountNumber;

	private String bankName;

	private String bankIfsc;

	private String bankAccountType;

	private String profileImage;

	private String razorpayCustomerId;

	private Long dummySupplierId;


	private Long customerId;

	private String cancelledCheque;

	private String gstCertificate;

	private String msmeCertificate;

	private String panCard;


	private String kycUploadStatus;

	private String comment;


}
