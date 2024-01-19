package com.example.wth.repository;

import com.example.wth.entity.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends MongoRepository<Weather,String> {
   Optional<Weather> findByCampaignId(String campaignId);
   void deleteByCampaignId(String campaignId);
}
