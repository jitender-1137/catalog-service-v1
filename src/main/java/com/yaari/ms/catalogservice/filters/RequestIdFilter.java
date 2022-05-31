package com.yaari.ms.catalogservice.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
//@Order(30)
@Component
@WebFilter(urlPatterns = "/v1/*", description = "Set request id in MDC")
public class RequestIdFilter implements Filter {

	@Value("${ignore.request-id:false}")
	private boolean toIgnoreRequestIdGeneration;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		if (toIgnoreRequestIdGeneration)
			chain.doFilter(request, response);
		else {
			String requestId = ((HttpServletRequest) request).getHeader("requestId");
			if (!StringUtils.hasLength(requestId) && !isHealthApi(request) && !isDocumentationApi(request)) {
				requestId = UUID.randomUUID().toString();
				log.debug("Request Id not passed. {} is generating requestId {}", RequestIdFilter.class.getSimpleName(), requestId);
			}
			MDC.put("requestId", requestId);
			try {
				chain.doFilter(request, response);
			} finally {
				MDC.remove("requestId");
			}
		}
	}

	private boolean isDocumentationApi(ServletRequest request) {
		return ((HttpServletRequest) request).getRequestURI().contains("/v2/api-docs");
	}

	private boolean isHealthApi(ServletRequest request) {
		return ((HttpServletRequest) request).getRequestURI().contains("/actuator");
	}

}
