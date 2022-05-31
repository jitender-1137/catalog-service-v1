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
@Table(name = "supplier_complaint")
@NoArgsConstructor
@Getter
@Setter
public class SupplierComplaint {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "supplier_id")
	private Long supplierId;

	@Column(name = "complaint_category")
	private Long complaintCategory;

	@Column(name = "grievance")
	private String grievance;

	@Column(name = "handeling_team")
	private String handelingTeam;

	@Column(name = "action")
	private String action;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
