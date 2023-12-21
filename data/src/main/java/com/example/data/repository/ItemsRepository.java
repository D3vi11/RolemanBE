package com.example.data.repository;

import com.example.data.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends MongoRepository<Item,String> {
    List<Item> findItemsByName(String name);
    Optional<Item> findItemByName(String name);
}
