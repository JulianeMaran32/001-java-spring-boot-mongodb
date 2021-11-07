package com.jvm.project.ws.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvm.project.ws.workshopmongo.domain.User;
import com.jvm.project.ws.workshopmongo.dto.UserDTO;
import com.jvm.project.ws.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	// listar todos os usuarios
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();
		
		// converte cada item da lista original em um dto
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
		
	}
	
	// obter usuario por id
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		
		User obj = service.findById(id);		
		return ResponseEntity.ok().body(new UserDTO(obj));
		
	}

}