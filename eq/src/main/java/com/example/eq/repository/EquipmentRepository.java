package com.example.eq.repository;

import com.example.eq.entity.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    Optional<Equipment> findByUsernameAndCampaignIdAndCharacterName(String username, String campaignId, String characterName);
    void deleteByUsernameAndCampaignIdAndCharacterName(String username, String campaignId, String characterName);
    List<Equipment> findAllByCampaignId(String campaignId);
}
