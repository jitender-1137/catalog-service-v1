package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "catalog_log")
@NoArgsConstructor
@Getter
@Setter
public class CatalogLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "catalog_id")
	private Long catalogId;

	@Column(name = "action_by")
	private Long actionBy;

	@Column(name = "action_time")
	private long actionTime;

	@Column(name = "action")
	private String action;

	@Column(name = "cycle_completed")
	private Boolean cycleCompleted;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;


}
