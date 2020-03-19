package com.todoapplication.app.services;

import com.todoapplication.app.entities.RefreshToken;

public interface RefreshTokenService {
   RefreshToken createRefreshToken(RefreshToken refreshToken);
   void verifyExpiration(RefreshToken token);
   void deleteToken(String token);
   RefreshToken getRefreshToken(String token);
}
