package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "awb_status_log")
@NoArgsConstructor
@Getter
@Setter
public class AwbStatusLog {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "awb_number")
	private String awbNumber;

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "partner")
	private String partner;

	@Column(name = "source")
	private String source;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;


}
