package com.example.data.repository;

import com.example.data.entity.Enemy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnemyRepository extends MongoRepository<Enemy,String> {
    Optional<Enemy> findByName(String name);
}
