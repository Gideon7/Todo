package com.todoapplication.app.serviceImpls;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.app.dtos.TodoDTO;
import com.todoapplication.app.entities.Todos;
import com.todoapplication.app.repositories.TodoRepository;
import com.todoapplication.app.services.TodoService;
import com.todoapplication.app.utils.DateConverterUtil;
@Service
public class TodoServiceImpl implements TodoService{
	private static Logger logger=LoggerFactory.getLogger(TodoServiceImpl.class);
	
	@Autowired
	TodoRepository todoRepository;
	@Autowired
	DateConverterUtil dateConverter;
	
	@Override
	public Todos createTodo(TodoDTO todoDTO) {
		logger.info("Creating Todos");
		Todos todo=new Todos();
		try {
			if(todoDTO.getName().isEmpty() && todoDTO.getDescription().isEmpty()) {
				return todo;
			}
			Todos todo1=todoRepository.findById(todoDTO.getId());
			if(todo1==null) {
				todo.setId(todoDTO.getId());
				todo.setName(todoDTO.getName());
				todo.setDescription(todoDTO.getDescription());
				todo.setTargetDate(dateConverter.getDateFrom(todoDTO.getDate()));
				todo.setCompleted(false);
				todoRepository.save(todo);
			}
			return todo;
		}catch(Exception e) {
			logger.error("Error Adding");
			return null;
		}
		
	}

	

	@Override
	public int deleteTodo(int todoID) {
	      logger.info("Deleting Todo");
		try {
			Todos todo=todoRepository.findById(todoID);
			if(todo!=null) {
				todoRepository.deleteById(todoID);
				return 1;
			}
			else {
				return 2;
			}
			
		}catch(Exception e) {
			logger.error("Error Deleting Todo");
			return 0;
		}
	}

	@Override
	public Todos getTodoById(int todoID) {
		// TODO Auto-generated method stub
		Todos todo=todoRepository.findById(todoID);
		return todo;
	}

//	@Override
//	public Todos getTodoByIdAndUserID(int id, int userID) {
//		try {
//			Todos todo =todoRepository.findByIdAndUserID(id, userID);
//			if(todo!=null) {
//				return todo;
//			}
//			else {
//				return null;
//			}
//		}catch(Exception e) {
//			logger.error("Unable To Fetch Todo");
//			return null;
//		}
//		
//	}

	@Override
	public List<Todos> getAllTodos() {	
		return todoRepository.findAll();
	}



	@Override
	public int updateTodo(Todos todo) {
		logger.info("Updating Todo");
		try {
			todoRepository.save(todo);
			return 1;
		}catch(Exception e) {
			logger.error("Unable To Update Todo" + e);
			return 0;
		}
	}

}
