package com.yaari.ms.catalogservice.filters;

import com.yaari.ms.catalogservice.utility.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
//@Order(30)
@Component
@WebFilter(urlPatterns = "/*", description = "Validate client id value in incoming request")
public class ClientIdFilter implements Filter {
	@Value("${validate.client-id.header:true}")
	private boolean toValidateClientIdHeader;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		String clientIdHeader = ((HttpServletRequest) request).getHeader(ApplicationConstants.CLIENT_ID_STR);
		validateClientIdHeader(clientIdHeader);
		chain.doFilter(request, response);
	}

	private void validateClientIdHeader(String clientIdHeader) {
		if (!toValidateClientIdHeader)
			return;
		log.info("Validating client id {}", clientIdHeader);
		//todo validate
	}

}
