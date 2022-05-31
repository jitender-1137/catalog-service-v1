package com.yaari.ms.catalogservice.data.co;

import com.yaari.ms.catalogservice.data.validation.fileurl.ValidUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class UpdateCategoryCO implements ValidationCO {

	private Long id;
	@ValidUrl
	private String categoryPageBannerImage;
	//Only for L1
	@ValidUrl
	private String homepageBannerImage;
	@Size(max = 2000)
	private String description;
	@NotBlank
	private String updatedByUsername;
	@NotBlank
	private String status;
	//Only for L3
	@ValidUrl
	private String templateFileUrl;
}

