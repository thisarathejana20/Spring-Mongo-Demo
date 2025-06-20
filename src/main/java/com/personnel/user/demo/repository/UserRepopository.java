package com.personnel.user.demo.repository;

import com.personnel.user.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepopository extends MongoRepository<User,Long> {
}
