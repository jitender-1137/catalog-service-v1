package com.yaari.ms.catalogservice.data.co;

import com.yaari.ms.catalogservice.data.validation.fileurl.ValidUrl;
import com.yaari.ms.catalogservice.data.validation.level.ValidLevel;
import com.yaari.ms.catalogservice.data.validation.templateUrl.ValidTemplateUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
@ValidTemplateUrl
public class CategoryCO {

	@NotBlank
	private String name;
	private Long parentCategoryId;
	@NotBlank
	@ValidUrl
	private String categoryPageBannerImage;
	@ValidUrl
	private String homepageBannerImage;
	@Size(max = 2000)
	private String description;
	@NotBlank
	private String createdByUsername;
	private String status;
	@ValidLevel
	private String level;
	private String templateFileUrl;

	public CategoryLevel getLevel() {
		if (this.level != null && CategoryLevel.contains(this.level))
			return CategoryLevel.valueOf(this.level);
		else
			return null;
	}
}

