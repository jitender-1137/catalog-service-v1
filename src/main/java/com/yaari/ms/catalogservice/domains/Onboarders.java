package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "onboarders")
@NoArgsConstructor
@Getter
@Setter
public class Onboarders {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;


}
