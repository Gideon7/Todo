package com.todoapplication.app.services;

import java.util.List;

import com.todoapplication.app.dtos.TodoDTO;
import com.todoapplication.app.entities.Todos;

public interface TodoService {
  Todos createTodo(TodoDTO todoDTO);
  int updateTodo(Todos todo);
  int deleteTodo(int todoID);
  Todos getTodoById(int todoID);
//  Todos getTodoByIdAndUserID(int id, int userID);
  List<Todos> getAllTodos();
}
