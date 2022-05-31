package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dummy_suppliers")
@NoArgsConstructor
@Getter
@Setter
public class DummySuppliers {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;


}
