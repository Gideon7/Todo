package com.todoapplication.app.configs;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.todoapplication.app.exceptions.InvalidTokenRequestFilterException;
import com.todoapplication.app.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Earnest
 *
 */
@Component
public class JwtTokenProvider {
	 private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	    @Value("${app.jwt.secret}")
	    private String jwtSecret;

	    @Value("${app.jwt.expiration}")
	    private Long jwtExpirationInMs;

	    @Value("${app.jwt.claims.refresh.name}")
	    private String jwtClaimRefreshName;

	    /**
	     * Generates a token from a principal object. Embed the refresh token in the jwt
	     * so that a new jwt can be created
	     */
	    public String generateToken(CustomUserDetails customUserDetails) {
	        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
	        return Jwts.builder()
	                .setSubject(Long.toString(customUserDetails.getId()))
	                .setIssuedAt(Date.from(Instant.now()))
	                .setExpiration(Date.from(expiryDate))
	                .signWith(SignatureAlgorithm.HS512, jwtSecret)
	                .compact();
	    }

	    /**
	     * Generates a token from a principal object. Embed the refresh token in the jwt
	     * so that a new jwt can be created
	     */
	    public String generateTokenFromUserId(Long userId) {
	        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
	        return Jwts.builder()
	                .setSubject(Long.toString(userId))
	                .setIssuedAt(Date.from(Instant.now()))
	                .setExpiration(Date.from(expiryDate))
	                .signWith(SignatureAlgorithm.HS512, jwtSecret)
	                .compact();
	    }
	    
	    /**
	     * Generates refresh token
	     * 
	     */
	    public String generateRefreshToken(String token) {
	    	Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
	    	Claims claim=Jwts.parser()
	    			.setSigningKey(jwtSecret)
	    			.parseClaimsJws(token)
	    			.getBody();
	    	claim.setIssuedAt(Date.from(Instant.now()));
	    	claim.setExpiration(Date.from(expiryDate));
			return Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	    }


	    /**
	     * Returns the user id encapsulated within the token
	     */
	    public Long getUserIdFromJWT(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(jwtSecret)
	                .parseClaimsJws(token)
	                .getBody();

	        return Long.parseLong(claims.getSubject());
	    }

	    /**
	     * Validates if a token has the correct unmalformed signature and is not expired or unsupported.
	     */
	    public boolean validateToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException ex) {
	            logger.error("Invalid JWT signature");
	            throw new InvalidTokenRequestFilterException("JWT", authToken, "Incorrect signature");
	        } catch (MalformedJwtException ex) {
	            logger.error("Invalid JWT token");
	            throw new InvalidTokenRequestFilterException("JWT", authToken, "Malformed jwt token");
	        } catch (ExpiredJwtException ex) {
	            logger.error("Expired JWT token");
	            throw new InvalidTokenRequestFilterException("JWT", authToken, "Token expired. Refresh required");
	        } catch (UnsupportedJwtException ex) {
	            logger.error("Unsupported JWT token");
	            throw new InvalidTokenRequestFilterException("JWT", authToken, "Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            logger.error("JWT claims string is empty.");
	            throw new InvalidTokenRequestFilterException("JWT", authToken, "Illegal argument token");
	        }
	    }

	    /**
	     * Return the jwt expiration for the client so that they can execute
	     * the refresh token logic appropriately
	     */
	    public Long getExpiryDuration() {
	        return jwtExpirationInMs;
	    }
	    private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			Calendar c=Calendar.getInstance();
			return expiration.before(c.getTime());
		}

		private Date getExpirationDateFromToken(String token) {
			// TODO Auto-generated method stub
			Claims claim= Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJwt(token)
					.getBody();
			return claim.getExpiration();
		}

		private Boolean ignoreTokenExpiration(String token) {
			// here you specify tokens, for that the expiration is ignored
			return false;
		}
		public Boolean canTokenBeRefreshed(String token) {
			return (!isTokenExpired(token) || ignoreTokenExpiration(token));
		}

}
