package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shiprocket_req_resp_log")
@NoArgsConstructor
@Getter
@Setter
public class ShiprocketReqRespLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "order_item_id")
	private String orderItemId;

	@Column(name = "req_body")
	private String reqBody;

	@Column(name = "response_data")
	private String responseData;

	@Column(name = "api_type")
	private String apiType;

	@Column(name = "created_at")
	private String createdAt;


}
