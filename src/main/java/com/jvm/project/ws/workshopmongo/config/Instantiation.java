package com.jvm.project.ws.workshopmongo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.jvm.project.ws.workshopmongo.domain.User;
import com.jvm.project.ws.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	// injetar o user repository para fazer conexão com o banco de dados
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		// limpa a coleção no mongodb
		userRepository.deleteAll();		
		
		// instanciar os objetos e incluir no banco de dados
		// id = null porque o banco de dados quem vai importar
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		// salvar os objetos na coleção no mongodb
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
	}

}