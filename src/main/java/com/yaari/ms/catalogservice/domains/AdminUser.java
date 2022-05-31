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
@Table(name = "admin_user")
@NoArgsConstructor
@Getter
@Setter
public class AdminUser {
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "hashed_password")
	private String hashedPassword;

	@Column(name = "is_active")
	private Boolean isActive;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "admin_role")
	private String adminRole;

	@Column(name = "admin_designation")
	private String adminDesignation;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "reset_token_expiry")
	private int resetTokenExpiry;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

}
