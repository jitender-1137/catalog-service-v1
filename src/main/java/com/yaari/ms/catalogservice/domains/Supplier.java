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
@Table(name = "supplier")
@NoArgsConstructor
@Getter
@Setter
public class Supplier {
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "hashed_password")
	private String hashedPassword;

	@Column(name = "is_active")
	private Boolean isActive;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name_pan_card")
	private String namePanCard;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "has_gst")
	private Boolean hasGst;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "type")
	private String type;

	@Column(name = "price_range_min")
	private Long priceRangeMin;

	@Column(name = "price_range_max")
	private Long priceRangeMax;

	@Column(name = "average_monthly_stock")
	private Long averageMonthlyStock;

	@Column(name = "referrer")
	private String referrer;

	@Column(name = "address_id")
	private Long addressId;

	@Column(name = "primary_category_id")
	private Long primaryCategoryId;

	@Column(name = "onboarder_id")
	private Long onboarderId;

	@Column(name = "approved")
	private Boolean approved;

	@Column(name = "bank_account_name")
	private String bankAccountName;

	@Column(name = "bank_account_number")
	private String bankAccountNumber;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_ifsc")
	private String bankIfsc;

	@Column(name = "bank_account_type")
	private String bankAccountType;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "razorpay_customer_id")
	private String razorpayCustomerId;

	@Column(name = "dummy_supplier_id")
	private Long dummySupplierId;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "cancelled_cheque")
	private String cancelledCheque;

	@Column(name = "gst_certificate")
	private String gstCertificate;

	@Column(name = "msme_certificate")
	private String msmeCertificate;

	@Column(name = "pan_card")
	private String panCard;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

	@Column(name = "kyc_upload_status")
	private String kycUploadStatus;

	@Column(name = "comment")
	private String comment;


}
