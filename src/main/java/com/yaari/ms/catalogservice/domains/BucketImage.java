package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bucket_image")
@NoArgsConstructor
@Getter
@Setter
public class BucketImage {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "bucket_id")
	private Long bucketId;


}
