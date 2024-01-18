package com.example.cp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CampaignDto {
    private final String id;
    private final String campaignName;
    private final String gameMasterUsername;
    private final List<String> playersUsernames;
}
