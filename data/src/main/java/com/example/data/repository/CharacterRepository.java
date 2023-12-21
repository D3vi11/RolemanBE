package com.example.data.repository;

import com.example.data.entity.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character,String> {
    Optional<Character> findByName(String name);
}
