package com.jvm.project.ws.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvm.project.ws.workshopmongo.domain.User;
import com.jvm.project.ws.workshopmongo.dto.UserDTO;
import com.jvm.project.ws.workshopmongo.exception.ObjectNotFoundException;
import com.jvm.project.ws.workshopmongo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	// obter usuario por id
	// GET
	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
	
	// inserir usuario
	// POST 
	public User insert(User obj) {
		return repository.insert(obj);
	}
	
	// deletar usuário 
	// DELETE
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(
				objDto.getId(), 
				objDto.getNome(), 
				objDto.getEmail());
	}
	
	
	// atualizar usuário
	// PUT

}