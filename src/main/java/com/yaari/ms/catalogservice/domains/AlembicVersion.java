package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alembic_version")
@NoArgsConstructor
@Getter
@Setter
public class AlembicVersion {
	@Id
	@Column(name = "version_num")
	private String versionNum;
}
