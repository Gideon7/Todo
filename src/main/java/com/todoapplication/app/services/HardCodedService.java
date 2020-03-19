package com.todoapplication.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import com.todoapplication.app.entities.Todos;

@Service
public class HardCodedService {
   private static List<Todos> todos=new ArrayList<>();
   private static int idCounter=0;
   
   static {
	   todos.add(new Todos(++idCounter,"MyTodos","Baking Bread",new Date(),false));
	   todos.add(new Todos(++idCounter,"MyTodos","Angular Learning",new Date(),false));
	   todos.add(new Todos(++idCounter,"MyTodos","Javascript brushing",new Date(),false));
	   todos.add(new Todos(++idCounter,"MyTodos","Peas and Pies",new Date(),false));
   }
   
  
   public List<Todos> findAll(){
	   return todos;
   }
}
