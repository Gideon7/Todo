package com.todoapplication.app.serviceImpls;

import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.todoapplication.app.entities.RefreshToken;
import com.todoapplication.app.exceptions.TokenRefreshException;
import com.todoapplication.app.repositories.RefreshTokenRepository;
import com.todoapplication.app.services.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	private static Logger logger=LoggerFactory.getLogger(RefreshTokenServiceImpl.class);
	
	 @Value("${app.token.refresh.duration}")
	 private Long refreshTokenDurationMs;
	 @Autowired
	 RefreshTokenRepository refreshTokenRepository;
	 

	@Override
	public RefreshToken createRefreshToken(RefreshToken refreshToken) {
		logger.info("Creating Refresh Token");
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		String token=generateRandomUUID();
        refreshToken.setToken(token);
		return refreshToken;
	}

	@Override
	public void verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
		
	}

	@Override
	public void deleteToken(String token) {
		// TODO Auto-generated method stub
		RefreshToken token1 =refreshTokenRepository.findByToken(token);
		if(token1!=null) {
			refreshTokenRepository.deleteByToken(token1);
		}
		
	}

	@Override
	public RefreshToken getRefreshToken(String token) {
		// TODO Auto-generated method stub
		return refreshTokenRepository.findByToken(token);
	}
	
	private static String generateRandomUUID() {
		return UUID.randomUUID().toString();
   }

}
