package com.personnel.user.demo.repository;

import com.personnel.user.demo.entity.User;
import com.personnel.user.demo.utill.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepository {
    private final MongoTemplate mongoTemplate;

    public Page<User> findByRole(String role, Pageable pageable) {
        if (!role.equals(Roles.ADMIN) && !role.equals(Roles.USER)) {
            throw new IllegalArgumentException("Invalid role");
        }
        Criteria criteria = Criteria.where("role").is(role);
        Query query = new Query(criteria).with(pageable);
        long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), User.class);
        List<User> users = mongoTemplate.find(query, User.class);
        return new PageImpl<>(users, pageable, count);
    }
}
