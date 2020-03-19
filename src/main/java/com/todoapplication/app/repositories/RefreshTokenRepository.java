package com.todoapplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapplication.app.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	RefreshToken findByToken(String token);

	void deleteByToken(RefreshToken token1);

}
