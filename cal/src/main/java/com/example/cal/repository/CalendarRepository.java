package com.example.cal.repository;

import com.example.cal.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar,String> {
    Optional<Calendar> findByCampaignId(String campaignId);
    void deleteByCampaignId(String campaignId);
}
