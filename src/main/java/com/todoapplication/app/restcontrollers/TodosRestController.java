package com.todoapplication.app.restcontrollers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.app.dtos.TodoDTO;
import com.todoapplication.app.entities.Todos;
import com.todoapplication.app.response.Response;
import com.todoapplication.app.services.TodoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(value="Todo Endpoints", description="These endpoints manages the Todo Creation Process")
public class TodosRestController {
	private static Logger logger=LoggerFactory.getLogger(TodosRestController.class);
	@Autowired
	TodoService todoService;
	
    @PostMapping(value="/todos")
    @ApiOperation(value = "This service creates a new todo", produces="application/json")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
	  logger.info("Adding Todo");
	  try {
		  Todos retValue=todoService.createTodo(todoDTO);
		  if(retValue!=null) {
			  return new ResponseEntity<>(retValue, HttpStatus.CREATED);
		  }
		  else {
			  return new ResponseEntity<>(retValue, HttpStatus.NOT_IMPLEMENTED);
		  }
	  }catch(Exception e) {
		  logger.error("Unable To Implement Create Todo");
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }
    }
    @PostMapping(value="/todos/update")
    @ApiOperation(value = "This service updates existing todo", produces="application/json")
    public ResponseEntity<?> updateTodo(@RequestBody Todos todo) {
	  logger.info("Adding Todo");
	  try {
		  int retValue=todoService.updateTodo(todo);
		  if(retValue==1) {
			  return new ResponseEntity<>(todo, HttpStatus.CREATED);
		  }
		  else {
			  return new ResponseEntity<>(retValue, HttpStatus.NOT_IMPLEMENTED);
		  }
	  }catch(Exception e) {
		  logger.error("Unable To Implement Create Todo");
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }
    }
    @DeleteMapping(value="/todo/{todoID}") 
    @ApiOperation(value = "This service deletes a todo", produces="application/json")
	public ResponseEntity<?> deleteTodoById(@PathVariable int todoID) {
    	logger.info("Deleting A Particular Todo");
    	try {
    		int todo=todoService.deleteTodo(todoID);
    		if(todo==1) {
    			Response response=new Response();
    			response.setStatus("200");
    			response.setMessage("Todo Deleted Successfully");
    			response.setDetails("Delete Operation");
    			return new ResponseEntity<>(response, HttpStatus.OK);
    		}
    		else if(todo==2) {
    			Response response=new Response();
    			response.setStatus("428");
    			response.setMessage("This Todo does not exist");
    			response.setDetails("Todo Not Found");
    			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    		}else {
    			return new ResponseEntity<>(todo, HttpStatus.NOT_IMPLEMENTED);
    		}
    	}catch(Exception e) {
    	  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);	
    	}
		
	}
    @GetMapping(value="/todo/{todoID}") 
    @ApiOperation(value = "This service gets a particular todo", produces="application/json")
	public ResponseEntity<?> getTodoById(@PathVariable int todoID) {
    	logger.info("Deleting A Particular Todo");
    	try {
    		return new ResponseEntity<>(todoService.getTodoById(todoID), HttpStatus.OK);
 
    	}catch(Exception e) {
    	  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);	
    	}
		
	}
    @GetMapping(value="/todos") 
    @ApiOperation(value = "This service gets all todos", produces="application/json")
	public ResponseEntity<List<?>> getAllTodos(){
    	logger.info("Getting All Todo");
    	try {
    		return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
 
    	}catch(Exception e) {
    		logger.error("Error Getting All Todos" +e);
    	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
    	}
		
	}
}
