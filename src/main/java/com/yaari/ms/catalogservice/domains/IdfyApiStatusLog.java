package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idfy_api_status_log")
@NoArgsConstructor
@Getter
@Setter
public class IdfyApiStatusLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "api_name")
	private String apiName;

	@Column(name = "task_id")
	private String taskId;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "request")
	private String request;

	@Column(name = "response")
	private String response;

	@Column(name = "request_ip")
	private String requestIp;

	@Column(name = "created_at")
	private long createdAt;


}
