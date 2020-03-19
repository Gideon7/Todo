package com.todoapplication.app.restcontrollers;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.todoapplication.app.configs.JwtTokenProvider;
import com.todoapplication.app.dtos.LoginDTO;
import com.todoapplication.app.dtos.UserRegistrationDTO;
import com.todoapplication.app.entities.Users;
import com.todoapplication.app.model.CustomUserDetails;
import com.todoapplication.app.response.JwtAuthenticationResponse;
import com.todoapplication.app.response.Response;
import com.todoapplication.app.services.CustomUserDetailsService;
import com.todoapplication.app.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(value="Authentication Endpoints", description="These endpoints manages the Todo Authentication Process")
public class AuthenticationRestController {
	private static Logger logger=LoggerFactory.getLogger(AuthenticationRestController.class);
	
	@Value("${app.jwt.header}")
    private String tokenRequestHeader;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserService usersService;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private CustomUserDetailsService userService;
	
	
	 @PostMapping(value="/auth/signIn")
     @ApiOperation(value = "This service creates a new auth token", produces="application/json")
	 public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO){
		  logger.info("Authenticating User");
		  Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken
				  (loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		  CustomUserDetails customUserDetails=(CustomUserDetails) authentication.getPrincipal();
		  SecurityContextHolder.getContext().setAuthentication(authentication);
		  
		  String authToken=tokenProvider.generateToken(customUserDetails);
		  return new ResponseEntity<>(new JwtAuthenticationResponse(authToken), HttpStatus.OK);
	}
	@PostMapping(value="/auth/singup")
    @ApiOperation(value = "This service creates a new auth token", produces="application/json")
	 public ResponseEntity<?> signup(@Valid @RequestBody UserRegistrationDTO userDTO){
		  logger.info("Creating new User");
		  try {
			  Users user=usersService.createUser(userDTO);
			  URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/registrationConfirmation/{email}").buildAndExpand(user.getEmail()).toUri();
				  return ResponseEntity.created(location).body(HttpStatus.OK+ ""+user);
			 
		  }catch(Exception e) {
			  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		  }
		 
	 }
	@GetMapping(value="/auth/refreshToken")
	@ApiOperation(value = "This service creates a new auth token", produces="application/json")
	public ResponseEntity<?> generateRefreshToken(HttpServletRequest request){
		String authToken=request.getHeader(tokenRequestHeader);
		final String token=authToken.substring(7);
		long userID=tokenProvider.getUserIdFromJWT(token);
		CustomUserDetails user=(CustomUserDetails) userService.loadUserById(Math.toIntExact(userID));
		
		if(tokenProvider.canTokenBeRefreshed(token)) {
			String refreshedToken=tokenProvider.generateRefreshToken(token);
			return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} 
		
		
	}
	
	
}
