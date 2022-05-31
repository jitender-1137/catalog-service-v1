package com.yaari.ms.catalogservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SuccessResponseDto<T> implements ResponseDto<T> {
	protected Integer code = HttpStatus.OK.value();
	protected T data;
	protected String message = null;
	protected long timestamp;
	protected String requestId;
	protected Long numItems;

	public SuccessResponseDto() {
		this(null, "Success");
	}

	public SuccessResponseDto(T data) {
		this(data, "Success");
	}

	public SuccessResponseDto(String message) {
		this.message = message;
	}

	public SuccessResponseDto(T data2, String successMessage) {
		this.data = data2;
		this.message = successMessage;
		this.requestId = MDC.get("requestId");
		this.timestamp = System.currentTimeMillis();
	}

	public SuccessResponseDto(T data2, String successMessage, Long numItems) {
		this(data2, successMessage);
		this.numItems = numItems;
	}
}