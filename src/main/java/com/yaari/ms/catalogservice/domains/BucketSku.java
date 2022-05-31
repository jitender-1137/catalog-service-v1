package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bucket_sku")
@NoArgsConstructor
@Getter
@Setter
public class BucketSku {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "bucket_id")
	private Long bucketId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "active_for_ranking")
	private Boolean activeForRanking;

	@Column(name = "bucket_preferred_rank")
	private Long bucketPreferredRank;


}
