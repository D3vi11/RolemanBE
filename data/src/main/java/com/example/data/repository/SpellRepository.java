package com.example.data.repository;

import com.example.data.entity.Spell;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpellRepository extends MongoRepository<Spell,String> {

    Optional<Spell> findByName(String name);
}
