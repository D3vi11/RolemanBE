package com.example.data.repository;

import com.example.data.entity.Enemy;
import com.example.data.enums.Rarity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnemyRepository extends MongoRepository<Enemy,String> {
    Optional<Enemy> findByName(String name);
    List<Enemy> findAllByRarityAndCr(Rarity rarity, Integer cr);
}
