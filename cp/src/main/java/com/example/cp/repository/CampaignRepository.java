package com.example.cp.repository;

import com.example.cp.entity.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CampaignRepository extends MongoRepository<Campaign,String> {

    @Query("{ 'campaignName' : ?0, '$or': [ { 'gameMasterUsername' : ?1 }, { 'playersUsernames': ?1 } ] }")
    Optional<Campaign> findByCampaignNameAndUsername(String campaignName, String playerName);
}
