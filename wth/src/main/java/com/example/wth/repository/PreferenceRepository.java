package com.example.wth.repository;

import com.example.wth.entity.Preference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends MongoRepository<Preference,String> {
    Optional<Preference> findByCampaignId(Integer campaignId);
}
