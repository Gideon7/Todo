package com.todoapplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapplication.app.entities.Todos;

@Repository
public interface TodoRepository extends JpaRepository<Todos, Integer>{

	Todos findById(int userID);

//	Todos findByIdAndUserID(int id, int userID);

}
