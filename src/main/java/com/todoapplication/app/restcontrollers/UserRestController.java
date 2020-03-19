package com.todoapplication.app.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(value="User Endpoints", description="These endpoints manages the User Creation Process")
public class UserRestController {
	private static Logger logger=LoggerFactory.getLogger(UserRestController.class);
	
	
}
