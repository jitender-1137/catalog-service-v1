package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_property")
@NoArgsConstructor
@Getter
@Setter
public class CategoryProperty {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "property_name")
	private String propertyName;

	@Column(name = "is_mandatory")
	private Boolean isMandatory;


}
