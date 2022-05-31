package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ActivateDeactivateLogDto {
	private Long id;

	private String type;

	private String fileName;

	private String ipAddress;

	private Long fileCounts;

	private Long isApproved;

	private String comment;

	private Long actionBy;

	private String s3FilePath;


}
