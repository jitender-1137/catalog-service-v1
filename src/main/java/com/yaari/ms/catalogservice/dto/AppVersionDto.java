package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AppVersionDto {
	private Long id;

	private String appVersion;

	private String appPackageName;

	private String appVersionCode;

	private Long daysToUpdate;

	private long lastUpdatedOn;

	private Boolean isForceUpdate;

	private String forceUpdateTitle;

	private String forceUpdateMessage;

	private String forceUpdateActionButton1;

	private String optionalUpdateTitle;

	private String optionalUpdateMessage;

	private String optionalUpdateActionButton1;

	private String optionalUpdateActionButton2;

	private Boolean isCurrentVersion;


	private String osName;


}
