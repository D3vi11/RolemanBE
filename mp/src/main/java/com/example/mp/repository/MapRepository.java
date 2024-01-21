package com.example.mp.repository;

import com.example.mp.entity.Map;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapRepository extends MongoRepository<Map,String> {
    Optional<Map> findByCampaignId(String campaignId);
    void deleteByCampaignId(String campaignId);
}
