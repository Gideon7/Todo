package com.todoapplication.app.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	 private final HandlerExceptionResolver resolver;

	 private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	 
	 @Autowired
	    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
	        this.resolver = resolver;
	    }
	 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("Preventing Unauthorized Access to Jwt Token");
		// TODO Auto-generated method stub
		 if (request.getAttribute("javax.servlet.error.exception") != null) {
	            Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
	            resolver.resolveException(request, response, null, (Exception) throwable);
	        }
	       response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

}
