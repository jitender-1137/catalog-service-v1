package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "app_version")
@NoArgsConstructor
@Getter
@Setter
public class AppVersion {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "app_version")
	private String appVersion;

	@Column(name = "app_package_name")
	private String appPackageName;

	@Column(name = "app_version_code")
	private String appVersionCode;

	@Column(name = "days_to_update")
	private Long daysToUpdate;

	@Column(name = "last_updated_on")
	private long lastUpdatedOn;

	@Column(name = "is_force_update")
	private Boolean isForceUpdate;

	@Column(name = "force_update_title")
	private String forceUpdateTitle;

	@Column(name = "force_update_message")
	private String forceUpdateMessage;

	@Column(name = "force_update_action_button_1")
	private String forceUpdateActionButton1;

	@Column(name = "optional_update_title")
	private String optionalUpdateTitle;

	@Column(name = "optional_update_message")
	private String optionalUpdateMessage;

	@Column(name = "optional_update_action_button_1")
	private String optionalUpdateActionButton1;

	@Column(name = "optional_update_action_button_2")
	private String optionalUpdateActionButton2;

	@Column(name = "is_current_version")
	private Boolean isCurrentVersion;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "os_name")
	private String osName;

	@Column(name = "updated_time")
	@LastModifiedDate
	private long updatedTime;
}
