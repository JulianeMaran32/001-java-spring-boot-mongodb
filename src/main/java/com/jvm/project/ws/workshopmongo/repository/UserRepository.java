package com.jvm.project.ws.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jvm.project.ws.workshopmongo.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
