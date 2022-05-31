package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class BucketDto {
	private Long id;

	private String name;

	private Long priority;

	private long endTime;

	private Long parentBucketId;

	private String description;

	private long startTime;

	private String deepLinkName;

	private Long bucketTypeId;

	private Long zoneId;

	private String bucketDesign;

	private Boolean active;

	private Date createdDate;

	private Date updatedDate;


}
