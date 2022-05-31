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
@Table(name = "zone")
@NoArgsConstructor
@Getter
@Setter
public class Zone {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "states")
	private String states;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "default")
	private Boolean isDefault;

	@Column(name = "default_city")
	private String defaultCity;

	@Column(name = "default_state")
	private String defaultState;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;


}
