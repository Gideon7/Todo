package com.todoapplication.app.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todoapplication.app.services.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	    @Value("${app.jwt.header}")
	    private String tokenRequestHeader;
	    @Value("${app.jwt.header.prefix}")
	    private String tokenRequestHeaderPrefix;
	    @Autowired
	    private JwtTokenProvider jwtTokenProvider;

	    @Autowired
	    private CustomUserDetailsService customUserDetailsService;

	    /**
	     * Filter the incoming request for a valid token in the request header
	     */
	    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 try {
	            String jwt = getJwtFromRequest(request);

	            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
	                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
	                UserDetails userDetails = customUserDetailsService.loadUserById(Math.toIntExact(userId));
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        } catch (Exception ex) {
	        	logger.error("Failed to set user authentication in security context: ", ex);
	            throw ex;
	        }

	        filterChain.doFilter(request, response);
	    }

	    /**
	     * Extract the token from the Authorization request header
	     */
	    private String getJwtFromRequest(HttpServletRequest request) {
	        String bearerToken = request.getHeader(tokenRequestHeader);
	        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
	        	logger.info("Extracted Token: " + bearerToken);
	            return bearerToken.replace(tokenRequestHeaderPrefix, "");
	        }
	        return null;
	    }

}
