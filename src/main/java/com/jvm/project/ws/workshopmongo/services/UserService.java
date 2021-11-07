package com.jvm.project.ws.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvm.project.ws.workshopmongo.domain.User;
import com.jvm.project.ws.workshopmongo.exception.ObjectNotFoundException;
import com.jvm.project.ws.workshopmongo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	// buscar usuario por id
	public User findById(String id) {		
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!")); 		
	}

}