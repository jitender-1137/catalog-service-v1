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
@Table(name = "activate_deactivate_log")
@NoArgsConstructor
@Getter
@Setter
public class ActivateDeactivateLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "file_counts")
	private Long fileCounts;

	@Column(name = "is_approved")
	private Long isApproved;

	@Column(name = "comment")
	private String comment;

	@Column(name = "action_by")
	private Long actionBy;

	@Column(name = "s3_file_path")
	private String s3FilePath;

	@Column(name = "uploaded_by")
	private Long uploadedBy;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;
}
