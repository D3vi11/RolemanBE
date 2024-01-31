package com.example.charr.repository;

import com.example.charr.entity.Character;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends MongoRepository<Character,String> {
    Optional<Character> findByNameAndCampaignIdAndUsername(String name, String campaignId, String username);
    void deleteByNameAndCampaignIdAndUsername(String name, String campaignId, String username);

    List<Character> findAllByCampaignIdAndUsername(String campaignId, String username);
}
