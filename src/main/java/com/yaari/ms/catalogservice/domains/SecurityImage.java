package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "security_image")
@NoArgsConstructor
@Getter
@Setter
public class SecurityImage {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "file_url")
	private String fileUrl;


}
