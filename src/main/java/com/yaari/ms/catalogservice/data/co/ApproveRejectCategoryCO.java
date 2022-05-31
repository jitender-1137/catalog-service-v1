package com.yaari.ms.catalogservice.data.co;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ApproveRejectCategoryCO implements ValidationCO {
	private Long id;
	@NotBlank
	private String checkerStatus;
	private String rejectReason;
	private String reviewedBy;
}
