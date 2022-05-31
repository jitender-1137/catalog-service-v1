package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_configurables")
@NoArgsConstructor
@Getter
@Setter
public class AppConfigurables {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "param_key")
	private String paramKey;

	@Column(name = "param_value")
	private Long paramValue;

}
