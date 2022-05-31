package com.yaari.ms.catalogservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
	private Long id;

	private String name;

	private Long parentCategoryId;

	private String homepageBannerImage;

	private String categoryPageBannerImage;

	private String description;

	private Long priority;

	private String slug;

	private String createdByName;
	private String createdByUsername;
	private String updatedByName;
	private String updatedByUsername;
	private String status;
	private String checkerStatus;
	private String level;
	private String reviewedBy;
	private Long reviewedAt;
	private String rejectReason;
	private Long version;
	private String templateFileUrl;
	private Long createdAt;
	private Long updatedAt;

	private CategoryMiniDto l1Parent;
	private CategoryMiniDto l2Parent;


}
