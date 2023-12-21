package com.example.data.repository;

import com.example.data.entity.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RuleRepository extends MongoRepository<Rule,String> {
    Optional<Rule> findByName(String name);
}
