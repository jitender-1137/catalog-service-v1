package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_validation_failed_log")
@NoArgsConstructor
@Getter
@Setter
public class BankValidationFailedLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "type_of_user")
	private String typeOfUser;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "bank_details")
	private String bankDetails;

	@Column(name = "resp_status")
	private String respStatus;

	@Column(name = "request_ip")
	private String requestIp;

	@Column(name = "created_at")
	private long createdAt;


}
