package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier_other_site_mapping")
@NoArgsConstructor
@Getter
@Setter
public class SupplierOtherSiteMapping {
	@Column(name = "supplier_id")
	@Id
	private Long id;

	@Column(name = "other_site_id")
	private Long otherSiteId;


}
