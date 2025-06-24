package com.personnel.user.demo.repository;

import com.personnel.user.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepopository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
}
