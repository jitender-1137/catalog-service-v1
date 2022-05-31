package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reseller")
@NoArgsConstructor
@Getter
@Setter
public class Reseller {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "security_image_url")
	private String securityImageUrl;

	@Column(name = "credits")
	private String credits;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "language_id")
	private Long languageId;

	@Column(name = "business_name")
	private String businessName;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "bank_account_number")
	private String bankAccountNumber;

	@Column(name = "bank_account_name")
	private String bankAccountName;

	@Column(name = "bank_ifsc")
	private String bankIfsc;

	@Column(name = "razorpay_customer_id")
	private String razorpayCustomerId;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "age_segment")
	private String ageSegment;

	@Column(name = "occupation")
	private String occupation;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

	@Column(name = "tutorial_watched")
	private Boolean tutorialWatched;

	@Column(name = "bank_account_image")
	private String bankAccountImage;


}
