package com.yaari.ms.catalogservice.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaari.ms.catalogservice.dto.ErrorResponseDto;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Deprecated
@Component
@WebFilter(
        urlPatterns = {"/v1/*"})
@Slf4j
public class ExtendedFilterTemp implements Filter {
    @Value("${validate.client-id.header:true}")
    private boolean toValidateClientIdHeader;

    private final MessageSource messageSource;
    private ObjectMapper mapper = new ObjectMapper();

    public ExtendedFilterTemp(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (httpRequest.getRequestURL().toString().contains("/v1/")) {
                String clientIdHeader = httpRequest.getHeader("client-id");
                Enumeration<String> headerNames = httpRequest.getHeaderNames();
                ArrayList<String> headers = new ArrayList<>();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        headers.add(headerNames.nextElement());
                    }
                }
                log.error("not valid ka checking");
                this.validateClientIdHeader(clientIdHeader, headers);
            }
            chain.doFilter(request, response);
        } catch (ServiceException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getErrorCode(), messageSource.getMessage(e.getErrorCode(), null, null));
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }

    // todo call from catalogservice
    private void validateClientIdHeader(String clientIdHeader, List<String> headers) {
        if (this.toValidateClientIdHeader) {
            log.info("Validating client id {}", clientIdHeader);
            List<String> validClientIds = Arrays.asList("node-supplier-admin-service", "node-supplier-portal-service");
            if (!headers.contains("client-id")){
				log.error("Client id is missing");
				throw new ServiceException("CS_35");
			}
			if (!StringUtils.hasLength(clientIdHeader)) {
                log.error("Empty client id passed");
                throw new ServiceException("CS_22");
            }
            if (!validClientIds.contains(clientIdHeader)) {
                log.error("Not a valid client id passed");
                throw new ServiceException("CS_21");
            }
        }
    }
}
