package com.todoapplication.app.services;

import org.slf4j.LoggerFactory;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todoapplication.app.entities.Users;
import com.todoapplication.app.model.CustomUserDetails;
import com.todoapplication.app.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private static Logger logger=LoggerFactory.getLogger(CustomUserDetailsService.class);
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("Fetching User with email => " + email );
		Optional<Users> dbuser=userRepository.findByEmail(email);
		return dbuser.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user email in the database for " + email));
	}
	
	public UserDetails loadUserById(int id) {
		logger.info("Fetching User with id => " + id);
		Optional<Users> dbuser=userRepository.findById(id);
		return dbuser.map(CustomUserDetails::new)
		.orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user email in the database for " + id));
		
	}

}
