package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@Getter
@Setter
public class Notification {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "reseller_id")
	private Long resellerId;

	@Column(name = "title")
	private String title;

	@Column(name = "title2")
	private String title2;

	@Column(name = "body")
	private String body;


}
